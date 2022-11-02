import React, { Component } from 'react'
import {
  View,
  ScrollView,
  StyleSheet,
  Dimensions,
  TouchableOpacity,
  Text,
  Button,
  Alert,
  Image
} from 'react-native'
import Chit from '../components/chit'
import fetch from 'node-fetch'
import ImagePicker from 'react-native-image-picker'

const WIDTH = Dimensions.get('window').width

/**
 * Class renders the profile screen and handles changes of the profile pic
 */
class ProfileScreen extends Component {
  constructor (props) {
    super(props)
    this.state = {
      profile: {
        given_name: '',
        family_name: '',
        email: '',
        recent_chits: [],
        user_id: ''
      },
      photo: null
    }
  }

  componentDidMount () {
    this.getProfileDetails()
  }

  componentDidUpdate (newProps) {
    if (newProps.screenProps.index === 3) {
      this.getProfileDetails()
    }
  }

  getProfileDetails () {
    return fetch(`${this.props.screenProps.api}/user/` +
      `${this.props.screenProps.id}`,
    {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => res.json())
      .then(json => {
        if (
          this.state.profile.given_name !== json.given_name ||
          this.state.profile.family_name !== json.family_name ||
          this.state.profile.email !== json.email ||
          this.state.profile.recent_chits.length !==
            json.recent_chits.length
        ) {
          this.setState({ profile: json })
        }
      },
      err => {
        console.error(err.name)
        Alert.alert('Fail loading')
      })
      .catch((error) => {
        console.error(error)
      })
  }

  handleChangeImage = () => {
    const options = {
      mediaType: 'photo'
    }
    ImagePicker.launchImageLibrary(options, response => {
      if (response.type === 'image/jpeg' || response.type === 'image/png') {
        this.setState({ photo: response })
        this.uploadProfilePic()
      } else {
        Alert.alert('Image must be of type JPEG or PNG')
      }
    })
  }

  uploadProfilePic () {
    return fetch(`${this.props.screenProps.api}/user/photo`, {
      method: 'POST',
      headers: {
        'Content-Type': this.state.photo.type,
        'X-Authorization': `${this.props.screenProps.token}`
      },
      body: this.state.photo
    })
      .then(response => {
        if (response.status === 201) {
          Alert.alert('Photo added')
        } else {
          Alert.alert('Photo not added')
        }
      })
      .catch(error => {
        console.error(error)
      })
  }

  render () {
    const contents = this.state.profile.recent_chits.map((chit, i) => {
      return (
        <Chit
          key={i}
          user={this.state.profile.user_id}
          chit={chit.chit_content}
          chit_id={chit.chit_id}
          location={chit.location}
          api={this.props.screenProps.api}
        />
      )
    })
    return (
      <ScrollView>
        <View style={styles.profileContainer}>
          <View style={styles.profileDetailsContainer}>
            <TouchableOpacity
              style={styles.picContainer}
              onPress={this.handleChangeImage}
            >
              <Image
                source={{
                  uri: `${this.props.screenProps.api}/user/` +
                    `${this.props.screenProps.id}/photo?time=` + new Date()
                }}
                style={styles.pic}
              />
            </TouchableOpacity>
            <View style={styles.personalDetailsContainer}>
              <View style={styles.detailsContainer}>
                <Text style={styles.details}>
                  {this.state.profile.given_name}
                </Text>
                <Text style={styles.details}>
                  {this.state.profile.family_name}
                </Text>
                <Text style={styles.details}>
                  {this.state.profile.email}
                </Text>
              </View>
              <View style={styles.editButtonContainer}>
                <Button
                  onPress={
                    () => this.props.navigation.navigate('EditProfileScreen',
                      {
                        given_name: this.state.profile.given_name,
                        family_name: this.state.profile.family_name,
                        email: this.state.profile.email
                      })
                  }
                  title='Edit profile'
                  color='black'
                />
              </View>
            </View>
          </View>
          <View style={styles.followContainer}>
            <TouchableOpacity
              style={styles.followButton}
              onPress={() => this.props.navigation.navigate('FollowersScreen')}
            >
              <Text style={styles.followText}>Followers</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.followButton}
              onPress={() => this.props.navigation.navigate('FollowingScreen')}
            >
              <Text style={styles.followText}>Following</Text>
            </TouchableOpacity>
          </View>
        </View>
        {contents}
      </ScrollView>
    )
  }
}

const styles = StyleSheet.create({
  profileContainer: {
    height: WIDTH * 0.5,
    backgroundColor: 'red',
    borderColor: 'black',
    borderWidth: 1,
    borderTopWidth: 0
  },
  profileDetailsContainer: {
    flex: 4,
    flexDirection: 'row'
  },
  followContainer: {
    flex: 1,
    flexDirection: 'row'
  },
  followButton: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center'
  },
  followText: {
    fontSize: 20,
    fontWeight: 'bold',
    textDecorationLine: 'underline'
  },
  picContainer: {
    width: WIDTH * 0.4,
    padding: 5,
    paddingLeft: 10,
    paddingRight: 0
  },
  pic: {
    width: WIDTH * 0.4 - 12,
    height: WIDTH * 0.4 - 12,
    backgroundColor: 'white',
    borderColor: 'black',
    borderWidth: 1
  },
  personalDetailsContainer: {
    flex: 1
  },
  detailsContainer: {
    flex: 3,
    padding: 5
  },
  details: {
    flex: 1,
    fontSize: 20,
    fontWeight: 'bold',
    textAlign: 'center',
    textAlignVertical: 'center',
    padding: 0,
    backgroundColor: 'red',
    margin: 2,
    color: 'black'
  },
  editButtonContainer: {
    flex: 1,
    padding: 5,
    paddingTop: 0
  }
})

export default ProfileScreen
