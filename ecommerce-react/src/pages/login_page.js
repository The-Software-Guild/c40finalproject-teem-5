import React, { Component } from 'react'
import { Button, Container, Row, Form } from 'react-bootstrap'
import '../styles/login_page.css'

const StateOption = ({ state }) => {
    return (
        <option value={state}>{state}</option>
    )
}

class LoginPage extends Component {

    states = [
        "AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FM", "FL",
        "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH",
        "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM",
        "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC",
        "SD", "TN", "TX", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY"
    ]

    state = {
        //customer
        name: "",   // max 50 char
        email: "",  // max 50 char
        phone: "",   // max 10 char and needs regex validation (?)

        //address
        street: "", // max 30 char
        city: "",   // max 30 char
        state: "",  // max 2 char (should be handled by <select> input method)
        postal: "", // max 10 char
        country: "" // max 30 char
    }


    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        });
        console.log(this.state)
    }

    handleState = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    render() {
        let { customer, selectCustomer, handleSubmit } = this.props
        return (
            <div className="Login-page">
                <div style={{ fontSize: "50px", color: "white" }}>Account</div>
                <div className="half-grid">
                    <div className="left">
                        <select value={customer} onChange={selectCustomer} defaultValue="1"
                            style={{ fontSize: "30px", width: "300px", marginBottom: "10px" }}>
                            <option value="1"> Customer 1 </option>
                            <option value="2"> Customer 2 </option>
                            <option value="3"> Customer 3 </option>
                        </select>
                    </div>
                    <div className="right">
                        <Form className="half-grid" style={{ border: "groove" }}>
                            <div className="right sign-up">
                                <Form.Group>
                                    <div style={{ color: "white", fontSize: "30px", margin: "10px" }}>Address</div>
                                    <Form.Row> {/*street*/}
                                        <label className="customer-label">Street:</label>
                                        <input type="text" name="street" className="customer-input" required
                                            pattern="[A-Za-z0-9 ]+" maxLength="30" onChange={this.handleChange} />
                                    </Form.Row>
                                    <Form.Row> {/*city*/}
                                        <label className="customer-label">City:</label>
                                        <input type="text" name="city" className="customer-input" required
                                            pattern="[A-Za-z ]+" maxLength="30" onChange={this.handleChange} />
                                    </Form.Row>
                                    <Form.Row> {/*state*/}
                                        <label className="customer-label">State:</label>
                                        <select type="text" name="state" className="customer-input" required onChange={this.handleChange}  >
                                            <option disabled selected>Select a State</option>
                                            {this.states.map((state, i) => {
                                                return <StateOption state={state} key={i} />
                                            })}
                                        </select>
                                    </Form.Row>
                                    <Form.Row> {/*postal*/}
                                        <label className="customer-label">Postal:</label>
                                        <input type="text" name="postal" className="customer-input" required
                                            pattern="[A-Z0-9-]+" maxLength="10" onChange={this.handleChange} />
                                    </Form.Row>
                                    <Form.Row> {/*country*/}
                                        <label className="customer-label">Country:</label>
                                        <input type="text" name="country" className="customer-input" required
                                            pattern="[A-Za-z ]+" maxLength="30" onChange={this.handleChange} />
                                    </Form.Row>
                                </Form.Group>
                            </div>
                            <div className="left sign-up">
                                <Form.Group>
                                    <div style={{ color: "white", fontSize: "30px", margin: "10px" }}>Customer</div>
                                    <Form.Row> {/*name*/}
                                        <label className="customer-label">Name:</label>
                                        <input type="text" name="name" className="customer-input" required
                                            pattern="[A-Za-z ]+" maxLength="50" onChange={this.handleChange} />
                                    </Form.Row>
                                    <Form.Row> {/*email*/}
                                        <label className="customer-label">Email:</label>
                                        <input type="email" name="email" className="customer-input" required onChange={this.handleChange} />
                                    </Form.Row>
                                    <Form.Row> {/*phone*/}
                                        <label className="customer-label">Phone:</label>
                                        <input type="tel" name="phone" className="customer-input" required
                                            pattern="[0-9]{3}-[0-9]{4}" required onChange={this.handleChange} />
                                    </Form.Row>
                                </Form.Group>
                            </div>
                            <Button type="submit"
                                onSubmit={(event) => {
                                    handleSubmit(event,
                                        this.state.name,
                                        this.state.email,
                                        this.state.phone,
                                        this.state.street,
                                        this.state.city,
                                        this.state.state,
                                        this.state.postal,
                                        this.state.country
                                        )
                                }} >Create Account</Button>
                        </Form>
                    </div>
                </div>
            </div>
        )
    }
}

export default LoginPage