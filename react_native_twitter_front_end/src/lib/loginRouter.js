import { createStackNavigator } from 'react-navigation-stack'
import { createAppContainer } from 'react-navigation'
import Login from '../screens/login'
import SignUp from '../screens/signup'

const LoginStack = createStackNavigator({
  LoginScreen: Login,
  SignUpScreen: SignUp
},
{
  headerMode: 'none',
  initialRouteName: 'LoginScreen'
})

export default createAppContainer(LoginStack)
