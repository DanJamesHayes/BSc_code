const constraints = {
  email: {
    presence: true,
    email: {
      message: '^Please enter a valid email address'
    }
  },
  givenName: {
    presence: true
  },
  surname: {
    presence: true
  },
  password: {
    presence: true,
    length: {
      minimum: 5,
      maximum: 20
    }
  },
  confirmPassword: {
    equality: {
      attribute: 'password',
      comparator: function (v1) {
        return v1.confirmPassword === v1.password
      }
    }
  },
  loginPassword: {
    presence: true
  },
  chit: {
    presence: true,
    length: {
      maximum: 141
    }
  },
  editEmail: {
    email: {
      message: '^Please enter a valid email address'
    }
  }
}

export default constraints
