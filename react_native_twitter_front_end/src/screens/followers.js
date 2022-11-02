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
 * Class presents followers of the logged in user
 */
class FollowersScreen extends Component {
  constructor (props) {
    super(props)
    this.state = {
      followers: []
    }
    this.getFollowers = this.getFollowers.bind(this)
  }

  componentDidMount () {
    this.getFollowers()
  }

  getFollowers () {
    return fetch(`${this.props.screenProps.api}/user/` +
      `${this.props.screenProps.id}/followers?time=` + new Date(),
    {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => res.json())
      .then(json => {
        this.setState({ followers: json })
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
    const followers = this.state.followers.map((follower, i) => {
      const fullName = `${follower.given_name} ${follower.family_name}`
      return (
        <TouchableOpacity
          key={i}
          onPress={
            () => this.props.navigation.navigate('UserScreen',
              { user_id: follower.user_id })
          }
        >
          <User
            name={fullName}
            email={follower.email}
            id={follower.user_id}
            api={this.props.screenProps.api}
          />
        </TouchableOpacity>
      )
    })
    return (
      <View style={styles.followers}>
        <View style={styles.titleBar}>
          <Text style={styles.title}>Followers</Text>
        </View>
        <ScrollView>
          {followers}
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

export default FollowersScreen
