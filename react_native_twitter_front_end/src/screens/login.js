import React, { Component } from 'react'
import {
  Text,
  TextInput,
  Button,
  View,
  StyleSheet,
  Alert
} from 'react-native'
import validator from '../lib/validator'
import fetch from 'node-fetch'

/**
 * Class to login to api and send returned id and token to the app container
 */
class LoginScreen extends Component {
  constructor (props) {
    super(props)
    this.state = {
      email: '',
      emailError: null,
      password: '',
      passwordError: null
    }
  }

  /**
   * Method checks if user input meets validation requirements
   */
  handleLogin = () => {
    const { email, password } = this.state
    const emailError = validator('email', email)
    const passwordError = validator('loginPassword', password)
    this.setState({
      emailError: emailError,
      passwordError: passwordError
    })

    if (!emailError &&
      !passwordError) {
      this.login()
    }
  }

  /**
   * Method sends user input to api and passes account details to app container
   * if details are recognised
   */
  login () {
    return fetch(`${this.props.api}/login?time=` + new Date(),
      {
        method: 'POST',
        body: JSON.stringify({
          email: this.state.email,
          password: this.state.password
        }),
        headers: { 'Content-Type': 'application/json' }
      })
      .then(res => res.json())
      .then(
        json => {
          this.props.setId(json.id)
          this.props.setToken(json.token)
          this.props.onLoginPress()
        },
        err => {
          console.error(err)
          Alert.alert('Email and/or password not recognised')
        })
      .catch((error) => {
        console.error(error)
      })
  }

  /**
   * Method renders login form
   */
  render () {
    const {
      emailError,
      passwordError
    } = this.state

    return (
      <View style={styles.screenView}>
        <Text style={styles.title}>CHITTR</Text>
        <Text style={styles.heading}>Login</Text>
        <TextInput
          style={styles.input}
          autoCapitalize='none'
          keyboardType='email-address'
          returnKeyType='next'
          placeholder='Email'
          onChangeText={(email) => this.setState({ email })}
        />
        <Text style={styles.inputError}>{emailError || null}</Text>
        <TextInput
          secureTextEntry
          style={styles.input}
          autoCapitalize='none'
          returnKeyType='go'
          placeholder='Password'
          onChangeText={(password) => this.setState({ password })}
        />
        <Text style={styles.inputError}>{passwordError || null}</Text>
        <View style={styles.button}>
          <Button
            onPress={this.handleLogin}
            title='Submit'
            color='black'
          />
        </View>
        <View style={styles.button}>
          <Button
            onPress={() => this.props.onSignUpPress()}
            title='Create new account'
            color='red'
          />
        </View>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  screenView: {
    flex: 1,
    justifyContent: 'center',
    padding: 10,
    backgroundColor: 'red'
  },
  title: {
    fontSize: 50,
    alignSelf: 'center',
    color: 'white',
    borderColor: 'white',
    borderWidth: 4,
    padding: 4,
    paddingLeft: 10,
    marginBottom: 40
  },
  heading: {
    fontSize: 30,
    fontWeight: 'bold',
    marginBottom: 20,
    alignSelf: 'center'
  },
  input: {
    height: 40,
    backgroundColor: 'white',
    borderColor: 'red',
    borderWidth: 1,
    fontSize: 20,
    padding: 0,
    paddingLeft: 10
  },
  createText: {
    fontWeight: 'bold'
  },
  inputError: {
    marginBottom: 10
  },
  button: {
    marginBottom: 40,
    alignItems: 'center'
  }
})

export default LoginScreen
