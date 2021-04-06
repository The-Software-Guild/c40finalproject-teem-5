import React, { Component } from 'react'
import { Button } from 'react-bootstrap'
class LoginPage extends Component {


    render() {
        let { customer, selectCustomer, address, selectAddress } = this.props
        return (
            <div className="App-page App-header">
                <div style={{marginBottom:"10px"}}>Log In</div>
                <select value={customer} onChange={selectCustomer} style={{ fontSize: "30px", width: "300px", marginBottom: "10px" }}>
                    <option selected disabled> Select a Customer </option>
                    <option value="1"> Customer 1 </option>
                    <option value="2"> Customer 2 </option>
                    <option value="3"> Customer 3 </option>
                </select>
                <select value={address} onChange={selectAddress} style={{ fontSize: "30px", width: "300px", marginBottom: "10px" }}>
                    <option selected disabled> Select an Address </option>
                    <option value="1"> Address 1 </option>
                    <option value="2"> Address 2 </option>
                    <option value="3"> Address 3 </option>
                </select>
            </div>
        )
    }
}

export default LoginPage