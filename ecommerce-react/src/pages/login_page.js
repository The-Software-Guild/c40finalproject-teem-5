import React, { Component } from 'react'
import { Button, Form } from 'react-bootstrap'
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
                <div style={{ fontWeight: "bold", color: "gold", fontSize: "60px"}}>Account</div>
                <div className="half-grid">
                    <div className="left">
                    <div>
                        <label style={{ fontWeight: "bold", color: "gold", textAlign:"center", fontSize:"40px"}}>LOGIN</label>
                        <br />
                        <select value={customer} onChange={selectCustomer} defaultValue="1"
                            style={{ fontSize: "30px", width: "300px", marginBottom: "10px" }}>
                            <option value="1"> Customer 1 </option>
                            <option value="2"> Customer 2 </option>
                            <option value="3"> Customer 3 </option>
                        </select>
                        </div>
                    </div>
                    <div className="right">
                        <Form className="half-grid" style={{ border: "groove", padding: "40px" }}>
                            <div className="right sign-up">
                                <Form.Group>
                                    <div style={{ fontWeight: "bold", color: "gold", fontSize: "40px", margin: "10px" }}>Address</div>
                                    <Form.Row> {/*street*/}
                                        <Form.Label className="customer-label">Street:</Form.Label>
                                        <Form.Control type="text" name="street" className="customer-input" required
                                            pattern="[A-Za-z0-9 ]+" maxLength="30" onChange={this.handleChange} />
                                    </Form.Row>
                                    <br />
                                    <Form.Row> {/*city*/}
                                        <Form.Label className="customer-label">City:</Form.Label>
                                        <Form.Control type="text" name="city" className="customer-input" required
                                            pattern="[A-Za-z ]+" maxLength="30" onChange={this.handleChange} />
                                    </Form.Row>
                                    <br />
                                    <Form.Row> {/*state*/}
                                        <Form.Label className="customer-label">State:</Form.Label>
                                        <select type="text" name="state" className="customer-input" required onChange={this.handleChange}  >
                                            <option disabled selected>Select a State</option>
                                            {this.states.map((state, i) => {
                                                return <StateOption state={state} key={i} />
                                            })}
                                        </select>
                                    </Form.Row>
                                    <br />
                                    <Form.Row> {/*postal*/}
                                        <Form.Label className="customer-label">Postal:</Form.Label>
                                        <Form.Control type="text" name="postal" className="customer-input" required
                                            pattern="[A-Z0-9-]+" maxLength="10" onChange={this.handleChange} />
                                    </Form.Row>
                                    <br />
                                    <Form.Row> {/*country*/}
                                        <Form.Label className="customer-label">Country:</Form.Label>
                                        <Form.Control type="text" name="country" className="customer-input" required
                                            pattern="[A-Za-z ]+" maxLength="30" onChange={this.handleChange} />
                                    </Form.Row>
                                </Form.Group>
                            </div>
                            <div className="left sign-up">
                                <Form.Group>
                                    <div style={{ fontWeight: "bold", color: "gold", fontSize: "40px", margin: "10px" }}>Customer</div>
                                    <Form.Row> {/*name*/}
                                        <Form.Label className="customer-label">Name:</Form.Label>
                                        <Form.Control type="text" name="name" className="customer-input" required
                                            pattern="[A-Za-z ]+" maxLength="50" onChange={this.handleChange} />
                                    </Form.Row>
                                    <Form.Row> {/*email*/}
                                        <Form.Label className="customer-label">Email:</Form.Label>
                                        <Form.Control type="email" name="email" className="customer-input" required onChange={this.handleChange} />
                                    </Form.Row>
                                    <Form.Row> {/*phone*/}
                                        <Form.Label className="customer-label">Phone:</Form.Label>
                                        <Form.Control type="tel" name="phone" className="customer-input" required
                                            pattern="[0-9]{3}-[0-9]{4}" onChange={this.handleChange} />
                                    </Form.Row>
                                    <br /><br /><br /><br /><br /><br /><br />
                                    <Button variant="danger" size="lg" block type="submit"
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
                                
                                </Form.Group>
                                <hr />
                            </div>
                        </Form>
                    </div>
                </div>
            </div>
        )
    }
}

export default LoginPage