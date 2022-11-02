import React, { Component } from 'react'
import {
  StyleSheet,
  View,
  Dimensions,
  Text,
  ScrollView,
  Image
} from 'react-native'
import Geocoder from 'react-native-geocoding'

const WIDTH = Dimensions.get('window').width

class Chit extends Component {
  constructor (props) {
    super(props)
    this.state = {
      location: 'No location given'
    }
  }

  componentDidMount () {
    if (this.props.location) {
      Geocoder.init('AIzaSyDCiQvAYgNJI9C8ItAXwhRblPxv6AmnWuk')
      Geocoder.from(this.props.location.latitude,
        this.props.location.longitude)
        .then(json => {
          var addressComponent = json.results[5].formatted_address
          this.setState({ location: addressComponent })
        })
        .catch(error => console.warn(error))
    }
  }

  render () {
    return (
      <View style={styles.chit}>
        <View style={styles.picView}>
          <View style={styles.container}>
            <Text style={styles.name}>User ID: {this.props.user}</Text>
          </View>
          <Image
            source={{
              uri: `${this.props.api}/chits/` +
                `${this.props.chit_id}/photo` +
                '?time=' + new Date()
            }}
            style={styles.pic}
          />
        </View>
        <View style={styles.textView}>
          <View style={styles.chitContainer}>
            <ScrollView
              nestedScrollEnabled
              style={styles.chitView}
            >
              <Text style={styles.chitText}>{this.props.chit}</Text>
            </ScrollView>
            <View style={styles.locationContainer}>
              <Text>{this.state.location}</Text>
            </View>
          </View>
        </View>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  chit: {
    flexDirection: 'row',
    width: WIDTH,
    height: WIDTH * 0.4,
    borderColor: 'black',
    borderBottomWidth: 2
  },
  container: {
    margin: 10
  },
  picView: {
    flex: 1,
    backgroundColor: 'red',
    alignItems: 'center',
    borderRightWidth: 1,
    borderColor: 'black'
  },
  textView: {
    flex: 2,
    padding: 5
  },
  name: {
    fontSize: 20,
    textAlign: 'center'
  },
  pic: {
    width: WIDTH * 0.25,
    height: WIDTH * 0.25,
    backgroundColor: 'white',
    borderColor: 'black',
    borderWidth: 1
  },
  chitText: {
    fontSize: 17,
    fontWeight: 'bold'
  },
  chitContainer: {
    flexDirection: 'column-reverse',
    marginTop: 5,
    marginLeft: 10,
    paddingBottom: 25,
    paddingTop: 5
  },
  locationContainer: {
    padding: 0
  }
})

export default Chit
