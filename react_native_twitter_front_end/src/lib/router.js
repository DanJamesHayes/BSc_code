import { createMaterialTopTabNavigator } from 'react-navigation-tabs'
import { createStackNavigator } from 'react-navigation-stack'
import { createAppContainer } from 'react-navigation'
import React from 'react'
import HomeScreen from '../screens/home'
import PostScreen from '../screens/post'
import SearchMain from '../screens/search'
import ProfileMain from '../screens/profile'
import ProfileFollowers from '../screens/followers'
import ProfileFollowing from '../screens/following'
import UserFollowers from '../screens/userFollowers'
import UserFollowing from '../screens/userFollowing'
import User from '../screens/user'
import EditProfile from '../screens/editProfile'
import Icon from 'react-native-vector-icons/Ionicons'

const ProfileStack = createStackNavigator({
  ProfileScreen: ProfileMain,
  EditProfileScreen: EditProfile,
  FollowersScreen: ProfileFollowers,
  FollowingScreen: ProfileFollowing
},
{
  headerMode: 'none',
  initialRouteName: 'ProfileScreen'
})

const SearchStack = createStackNavigator({
  SearchScreen: SearchMain,
  UserScreen: User,
  UserFollowersScreen: UserFollowers,
  UserFollowingScreen: UserFollowing
},
{
  headerMode: 'none',
  initialRouteName: 'SearchScreen'
})

const TabBar = createMaterialTopTabNavigator({
  Home: HomeScreen,
  Search: SearchStack,
  Post: PostScreen,
  Profile: ProfileStack
},
{
  tabBarOptions: {
    activeTintColor: 'white',
    showIcon: true,
    showLabel: false,
    style: {
      backgroundColor: 'red',
      borderWidth: 1,
      borderColor: 'black'
    }
  }
})

HomeScreen.navigationOptions = {
  tabBarIcon: ({ tintColor }) => (
    <Icon
      name='md-home'
      color={tintColor}
      size={25}
    />
  )
}

SearchStack.navigationOptions = {
  tabBarIcon: ({ tintColor }) => (
    <Icon
      name='md-search'
      color={tintColor}
      size={25}
    />
  )
}

PostScreen.navigationOptions = {
  tabBarIcon: ({ tintColor }) => (
    <Icon
      name='md-chatbubbles'
      color={tintColor}
      size={25}
    />
  )
}

ProfileStack.navigationOptions = {
  tabBarIcon: ({ tintColor }) => (
    <Icon
      name='md-person'
      color={tintColor}
      size={25}
    />
  )
}

export default createAppContainer(TabBar)
