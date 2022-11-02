import React, { Component } from 'react'
import {
  Text,
  View,
  StyleSheet,
  Button,
  TextInput,
  Alert
} from 'react-native'
import validator from '../lib/validator'
import fetch from 'node-fetch'

/**
 * Class patches the logged in users details with any chosen amendments
 */
class EditProfileScreen extends Component {
  constructor (props) {
    super(props)
    this.state = {
      email: this.props.navigation.state.params.email,
      emailError: null,
      givenName: this.props.navigation.state.params.given_name,
      surname: this.props.navigation.state.params.family_name,
      password: '',
      confirmPassword: '',
      confirmPasswordError: null,
      body: ''
    }
    this.createBody = this.createBody.bind(this)
  }

  handleEdit = () => {
    const { email, password, confirmPassword } = this.state
    const emailError = validator('editEmail', email)
    const confirmPasswordError = validator('confirmPassword', {
      confirmPassword: confirmPassword, password: password
    })
    this.setState({
      emailError: emailError,
      confirmPasswordError: confirmPasswordError
    })

    if (!emailError &&
      !confirmPasswordError) {
      this.createBody()
      setTimeout(() => { this.editUser() }, 1000)
      setTimeout(() => this.props.navigation.navigate('ProfileScreen'), 2000)
    }
  }

  createBody () {
    const temp = new Map()
    temp.set('given_name', this.state.givenName)
    temp.set('family_name', this.state.surname)
    temp.set('email', this.state.email)
    temp.set('password', this.state.password)
    const body = {}
    for (const [key, value] of temp.entries()) {
      if (value !== '') {
        body[key] = value
      }
    }
    this.setState({ body: JSON.stringify(body) })
  }

  editUser () {
    return fetch(`${this.props.screenProps.api}/user/` +
      `${this.props.screenProps.id}?time=` + new Date(),
    {
      method: 'PATCH',
      body: this.state.body,
      headers: {
        'Content-Type': 'application/json',
        'X-Authorization': `${this.props.screenProps.token}`
      }
    })
      .then((response) => {
        if (response.status === 201) {
          Alert.alert('Profile Edited')
        } else {
          Alert.alert('Failed to make edits')
        }
      })
      .catch((error) => {
        console.error(error)
      })
  }

  render () {
    const {
      emailError,
      confirmPasswordError
    } = this.state

    return (
      <View style={styles.screenView}>
        <Text style={styles.title}>Make amendments:</Text>
        <TextInput
          style={styles.input}
          autoCapitalize='none'
          keyboardType='email-address'
          returnKeyType='next'
          placeholder='Email'
          defaultValue={this.props.navigation.state.params.email}
          onChangeText={(email) => this.setState({ email })}
        />
        <Text> {emailError || null}</Text>
        <TextInput
          style={styles.input}
          returnKeyType='next'
          placeholder='First name'
          defaultValue={this.props.navigation.state.params.given_name}
          onChangeText={(givenName) => this.setState({ givenName })}
        />
        <Text> </Text>
        <TextInput
          style={styles.input}
          returnKeyType='next'
          placeholder='Surname'
          defaultValue={this.props.navigation.state.params.family_name}
          onChangeText={(surname) => this.setState({ surname })}
        />
        <Text> </Text>
        <TextInput
          style={styles.input}
          secureTextEntry
          autoCapitalize='none'
          placeholder='Password'
          returnKeyType='next'
          onChangeText={(password) => this.setState({ password })}
        />
        <Text> </Text>
        <TextInput
          style={styles.input}
          secureTextEntry
          autoCapitalize='none'
          placeholder='Confim password'
          returnKeyType='go'
          onChangeText={(confirmPassword) => this.setState({ confirmPassword })}
        />
        <Text> {confirmPasswordError || null}</Text>
        <Button
          onPress={this.handleEdit}
          title='Edit details'
          color='black'
        />
      </View>
    )
  }
}

const styles = StyleSheet.create({
  screenView: {
    flex: 1,
    justifyContent: 'center',
    padding: 10,
    paddingTop: 60,
    backgroundColor: 'red'
  },
  title: {
    fontSize: 26,
    fontWeight: 'bold',
    marginBottom: 10
  },
  input: {
    backgroundColor: 'white',
    marginTop: 10,
    fontSize: 20,
    paddingLeft: 10
  }
})

export default EditProfileScreen
