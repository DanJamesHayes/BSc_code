import React, { Component } from 'react'
import { AppRegistry } from 'react-native'
import LoginScreen from './src/screens/login'
import App from './src'
import { name as appName } from './app.json'
import SignUpScreen from './src/screens/signup'

/**
 * Class controls the login state and presents either the app, login or sign
 * up page dependent on the state.
 *
 */
class Chittr extends Component {
  constructor (props) {
    super(props)
    this.setId = this.setId.bind(this)
    this.setToken = this.setToken.bind(this)
    this.state = {
      isLoggedIn: false,
      isNotSignedUp: false,
      id: '',
      token: '',
      api: 'http://10.0.2.2:3333/api/v0.0.5'
    }
  }

  /**
   * Method to set the ID number of the logged in user. Can be called from the
   * props.screenProps throughout the app whilst the user is logged in
   * @param {number} newId
   */
  setId (newId) {
    this.setState({ id: newId })
  }

  /**
   * Method to set the token of the logged in user. Can be called from the
   * props.screenProps throughout the app whilst the user is logged in
   * @param {number} newToken
   */
  setToken (newToken) {
    this.setState({ token: newToken })
  }

  /**
   * render either the app, login or sign up page dependent on the current
   * state of the class
   */
  render () {
    if (this.state.isLoggedIn) {
      return (
        <App
          id={this.state.id}
          token={this.state.token}
          api={this.state.api}
          onLogoutPress={
            () => this.setState({ isLoggedIn: false })
          }
        />
      )
    } else {
      if (this.state.isNotSignedUp) {
        return (
          <SignUpScreen
            api={this.state.api}
            onSignUpPress={
              () => this.setState({ isNotSignedUp: false })
            }
          />
        )
      } else {
        return (
          <LoginScreen
            api={this.state.api}
            onSignUpPress={
              () => this.setState({ isNotSignedUp: true })
            }
            onLoginPress={
              () => this.setState({ isLoggedIn: true })
            }
            setId={this.setId}
            setToken={this.setToken}
          />
        )
      }
    }
  }
}

AppRegistry.registerComponent(appName, () => Chittr)
