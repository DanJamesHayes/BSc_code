import validate from 'validate.js'
import constraints from '../lib/constraints'

const validator = (field, value) => {
  const object = {}
  object[field] = value

  const constraint = {}
  constraint[field] = constraints[field]

  let result = validate({}, constraint)
  if (value !== '' && value != null) {
    result = validate(object, constraint)
  }
  if (result) {
    return result[field][0]
  }

  return null
}

export default validator
