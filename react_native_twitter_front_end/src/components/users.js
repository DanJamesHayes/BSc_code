import React, { Component } from 'react'
import { StyleSheet, View, Dimensions, Text, Image } from 'react-native'

const WIDTH = Dimensions.get('window').width

class User extends Component {
  render () {
    return (
      <View style={styles.userContainer}>
        <View style={styles.userPicContainer}>
          <Image
            source={{
              uri: `${this.props.api}/user/` +
                `${this.props.id}/photo?time=` + new Date()
            }}
            style={styles.userPic}
          />
        </View>
        <View style={styles.userDetailsContainer}>
          <Text style={styles.userDetails}>{this.props.name}</Text>
          <Text style={styles.userDetails}>{this.props.email}</Text>
          <Text style={styles.userDetails}>User ID: {this.props.id}</Text>
        </View>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  userContainer: {
    height: WIDTH * 0.4,
    flexDirection: 'row',
    backgroundColor: 'red',
    borderColor: 'black',
    borderWidth: 1,
    borderTopWidth: 0
  },
  userPicContainer: {
    width: WIDTH * 0.4,
    borderColor: 'black',
    borderRightWidth: 1,
    paddingTop: 5,
    paddingLeft: 5
  },
  userDetailsContainer: {
    padding: 5,
    justifyContent: 'center',
    alignItems: 'center',
    flex: 1
  },
  userPic: {
    height: WIDTH * 0.4 - 12,
    width: WIDTH * 0.4 - 12,
    backgroundColor: 'white',
    borderColor: 'black',
    borderWidth: 1
  },
  userDetails: {
    fontSize: 20,
    fontWeight: 'bold'
  }
})

export default User
