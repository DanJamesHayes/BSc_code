import React, { Component } from 'react'
import {
  View,
  StyleSheet,
  Text,
  ScrollView,
  Alert,
  TouchableOpacity
} from 'react-native'
import User from '../components/users'
import fetch from 'node-fetch'

/**
 * Class renders the details of accounts a chosen user is following
 */
class UserFollowingScreen extends Component {
  constructor (props) {
    super(props)
    this.state = {
      following: []
    }
    this.getFollowing = this.getFollowing.bind(this)
  }

  componentDidMount () {
    this.getFollowing()
  }

  getFollowing () {
    return fetch(`${this.props.screenProps.api}/user/` +
      `${this.props.navigation.state.params.user_id}/following` +
      '?time=' + new Date(),
    {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => res.json())
      .then(json => {
        this.setState({ following: json })
      },
      err => {
        console.log(err.name)
        Alert.alert('Fail loading')
      })
      .catch((error) => {
        console.error(error)
      })
  }

  render () {
    const following = this.state.following.map((follow, i) => {
      const fullName = `${follow.given_name} ${follow.family_name}`
      return (
        <TouchableOpacity
          key={i}
          onPress={
            () => this.props.navigation.navigate('UserScreen',
              { user_id: follow.user_id })
          }
        >
          <User
            name={fullName}
            email={follow.email}
            id={follow.user_id}
            api={this.props.screenProps.api}
          />
        </TouchableOpacity>
      )
    })
    return (
      <View style={styles.followers}>
        <View style={styles.titleBar}>
          <Text style={styles.title}>Following</Text>
        </View>
        <ScrollView>
          {following}
        </ScrollView>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  followers: {
    flex: 1
  },
  titleBar: {
    padding: 5,
    backgroundColor: 'red',
    borderColor: 'black',
    borderWidth: 1,
    borderTopWidth: 0,
    alignItems: 'center'
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold'
  }
})

export default UserFollowingScreen
