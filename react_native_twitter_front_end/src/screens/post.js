import React, { Component } from 'react'
import {
  View,
  StyleSheet,
  Button,
  TextInput,
  Alert,
  Text,
  PermissionsAndroid
} from 'react-native'
import validator from '../lib/validator'
import fetch from 'node-fetch'
import ImagePicker from 'react-native-image-picker'
import Geolocation from 'react-native-geolocation-service'

/**
 * Class handles the posting of chits including the options of adding an image
 * or the loaction
 */
class PostScreen extends Component {
  constructor (props) {
    super(props)
    this.state = {
      chit: '',
      chitError: null,
      addImage: false,
      photo: null,
      chit_id: 0,
      location: {
        longitude: 0,
        latitude: 0
      },
      locationPermission: false
    }
  }

  handleChit = () => {
    const { chit } = this.state
    const chitError = validator('chit', chit)
    this.setState({ chitError: chitError })

    if (!chitError) {
      this.postChit()
    }
  }

  handleAddImage = () => {
    const options = {
      mediaType: 'photo'
    }
    ImagePicker.launchImageLibrary(options, response => {
      if (response.type === 'image/jpeg' || response.type === 'image/png') {
        this.setState({ photo: response, addImage: true })
      } else {
        Alert.alert('Image must be of type JPEG or PNG')
      }
    })
  }

  postPhoto () {
    return fetch(`${this.props.screenProps.api}/user/` +
      `${this.props.screenProps.id}?time=` + new Date(),
    {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => res.json())
      .then(json => {
        fetch(`${this.props.screenProps.api}/chits/` +
         `${json.recent_chits[0].chit_id}/photo?time=` + new Date(), {
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
              this.setState({ photo: null, addImage: false })
            } else {
              Alert.alert('Photo not added')
              this.setState({ photo: null, addImage: false })
            }
          })
          .catch(error => {
            console.error(error)
          })
      },
      err => {
        console.error(err.name)
        Alert.alert('Fail loading')
      })
      .catch((error) => {
        console.error(error)
      })
  }

  postChit () {
    return fetch(`${this.props.screenProps.api}/chits?time=` + new Date(),
      {
        method: 'POST',
        body: JSON.stringify({
          timestamp: new Date().getTime(),
          chit_content: this.state.chit,
          location: this.state.location
        }),
        headers: {
          'Content-Type': 'application/json',
          'X-Authorization': `${this.props.screenProps.token}`
        }
      })
      .then((response) => {
        if (response.status === 201) {
          Alert.alert('You have chitt')
          this.setState({
            chit: '',
            location: {
              longitude: 0,
              latitude: 0
            }
          })
          if (this.state.addImage === true) {
            this.postPhoto()
          }
        } else {
          Alert.alert('Chitt failed')
        }
      })
      .catch((error) => {
        console.error(error)
      })
  }

  async requestLocationPermission () {
    try {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
        {
          title: 'Location Permission',
          message: 'Allow access to location permission',
          buttonNeutral: 'Ask me later',
          buttonNegative: 'Cancel',
          buttonPositive: 'OK'
        }
      )
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        return true
      } else {
        return false
      }
    } catch (err) {
      console.error(err)
    }
  }

  handleFindCoordinates = () => {
    if (!this.state.locationPermission) {
      this.state.locationPermission = this.requestLocationPermission()
    }

    Geolocation.getCurrentPosition(
      (position) => {
        this.setState({
          location: {
            longitude: position.coords.longitude,
            latitude: position.coords.latitude
          }
        })
        Alert.alert('Location added')
      },
      (error) => {
        Alert.alert(error.message)
      },
      {
        enableHighAccuracy: true,
        timeout: 20000,
        maximumAge: 1000
      }
    )
  }

  render () {
    const { chitError } = this.state

    return (
      <View style={styles.postContainer}>
        <View style={styles.submitContainer}>
          <View style={styles.buttonContainer}>
            <Button
              onPress={this.handleAddImage}
              title='Add image'
              color='black'
            />
          </View>
          <View style={styles.buttonContainer}>
            <Button
              onPress={this.handleFindCoordinates}
              title='Add location'
              color='black'
            />
          </View>
          <View style={styles.buttonContainer}>
            <Button
              onPress={this.handleChit}
              title='Post'
              color='black'
            />
          </View>
        </View>
        <View style={styles.textInputContainer}>
          <TextInput
            style={styles.textInput}
            multiline
            textAlignVertical='top'
            value={this.state.chit}
            onChangeText={(chit) => this.setState({ chit })}
          />
          <Text> {chitError || null}</Text>
        </View>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  postContainer: {
    flex: 1,
    flexDirection: 'column-reverse',
    backgroundColor: 'red',
    borderColor: 'black',
    borderWidth: 1,
    borderTopWidth: 0
  },
  submitContainer: {
    flexDirection: 'row'
  },
  buttonContainer: {
    flex: 1,
    padding: 5,
    paddingTop: 0
  },
  textInputContainer: {
    flex: 1,
    padding: 5,
    marginTop: 10
  },
  textInput: {
    flex: 1,
    backgroundColor: 'white',
    borderColor: 'black',
    borderWidth: 1,
    fontSize: 20
  }
})

export default PostScreen
