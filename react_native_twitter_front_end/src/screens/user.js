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

const WIDTH = Dimensions.get('window').width

/**
 * Class renders profile screen of other users
 */
class UserScreen extends Component {
  constructor (props) {
    super(props)
    this.state = {
      user: {
        given_name: '',
        family_name: '',
        email: '',
        recent_chits: [],
        user_id: 0
      },
      isFollowed: false,
      followed: []
    }
    this.handleFollow = this.handleFollow.bind(this)
  }

  /**
   * Call isFollowing() and getUserDetails() methods on load
   */
  componentDidMount () {
    this.isFollowing()
    this.getUserDetails()
  }

  /**
   * Method gets the details of the user id passed in the navigation params
   */
  getUserDetails () {
    return fetch(`${this.props.screenProps.api}/user/` +
      `${this.props.navigation.state.params.user_id}?time=` + new Date(),
    {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => res.json())
      .then(json => {
        this.setState({ user: json })
      },
      err => {
        console.log(err.name)
        Alert.alert('Fail loading')
      })
      .catch((error) => {
        console.error(error)
      })
  }

  /**
   * Method checks if user id passed in navigation params is following the user
   * that is logged into the app
   */
  isFollowing () {
    return fetch(`${this.props.screenProps.api}/user/` +
      `${this.props.screenProps.id}/following?time=` + new Date(),
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(res => res.json())
      .then(json => {
        this.setState({ followed: json })
      },
      err => {
        console.log(err.name)
        Alert.alert('Fail loading')
      })
      .catch((error) => {
        console.error(error)
      })
      .then(() => {
        this.state.followed.map((user) => {
          if (user.user_id === this.props.navigation.state.params.user_id) {
            this.setState({ isFollowed: true })
          }
        })
      })
  }

  /**
   * Method handles the pressing of the follow/unfollow button and updates the
   * api as required dependent on the current state
   */
  handleFollow () {
    if (this.state.isFollowed) {
      return fetch(`${this.props.screenProps.api}/user/` +
        `${this.props.navigation.state.params.user_id}/follow` +
        '?time=' + new Date(),
      {
        method: 'DELETE',
        headers: {
          'X-Authorization': `${this.props.screenProps.token}`
        }
      })
        .then(response => {
          if (response.status === 200) {
            this.setState({ isFollowed: false })
          } else {
            Alert.alert('User not unfollowed')
          }
        })
        .catch((error) => {
          console.error(error)
        })
    } else {
      return fetch(`${this.props.screenProps.api}/user/` +
        `${this.props.navigation.state.params.user_id}/follow?` +
        'time=' + new Date(),
      {
        method: 'POST',
        headers: {
          'X-Authorization': `${this.props.screenProps.token}`
        }
      })
        .then(response => {
          if (response.status === 200) {
            this.setState({ isFollowed: true })
          } else {
            Alert.alert('User not followed')
          }
        })
        .catch((error) => {
          console.error(error)
        })
    }
  }

  /**
   * Method renders the user page
   */
  render () {
    const contents = this.state.user.recent_chits.map((chit, i) => {
      return (
        <Chit
          key={i}
          user={this.state.user.user_id}
          chit={chit.chit_content}
          chit_id={chit.chit_id}
          api={this.props.screenProps.api}
        />
      )
    })
    return (
      <ScrollView>
        <View style={styles.userContainer}>
          <View style={styles.userDetailsContainer}>
            <View style={styles.picContainer}>
              <Image
                source={{
                  uri: `${this.props.screenProps.api}/user/` +
                    `${this.props.navigation.state.params.user_id}/photo` +
                    '?time=' + new Date()
                }}
                style={styles.pic}
              />
            </View>
            <View style={styles.personalDetailsContainer}>
              <View style={styles.detailsContainer}>
                <Text style={styles.details}>
                  {this.state.user.given_name}
                </Text>
                <Text style={styles.details}>
                  {this.state.user.family_name}
                </Text>
                <Text style={styles.details}>
                  {this.state.user.email}
                </Text>
              </View>
              <View style={styles.followButtonContainer}>
                <Button
                  onPress={this.handleFollow}
                  title={this.state.isFollowed ? 'Unfollow' : 'Follow'}
                  color='black'
                />
              </View>
            </View>
          </View>
          <View style={styles.followContainer}>
            <TouchableOpacity
              style={styles.followButton}
              onPress={
                () => this.props.navigation.navigate('UserFollowersScreen',
                  { user_id: this.state.user.user_id })
              }
            >
              <Text style={styles.followText}>Followers</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.followButton}
              onPress={
                () => this.props.navigation.navigate('UserFollowingScreen',
                  { user_id: this.state.user.user_id })
              }
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
  userContainer: {
    height: WIDTH * 0.5,
    backgroundColor: 'red',
    borderColor: 'black',
    borderWidth: 1,
    borderTopWidth: 0
  },
  userDetailsContainer: {
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
    fontWeight: 'bold'
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
  followButtonContainer: {
    flex: 1,
    padding: 5,
    paddingTop: 0
  }
})

export default UserScreen
