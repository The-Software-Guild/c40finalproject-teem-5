import React from 'react';
import { Card, Button, Container, Row, Col } from 'react-bootstrap';
import axios from 'axios';
import "./checkout_page.css"


const ListedItem = ({item, key }) =>{
    let total = item.price * item.quantity;
    console.log(total);

    return(

        <Card style={{width: '150px', backgroundColor:"lightblue", margin:"5px", display:"grid"}} >
            <Card.Body>
                <Card.Title>{item.title}</Card.Title>
                <div>
                    <br/>
                    Price: {item.price}
                    <br/>
                    Quantity: {item.quantity}
                    <br/>
                    <br/>
                    Total: {total} USD
                    <br/>
                </div>
            </Card.Body>
        </Card>

    );
}

class CheckoutPage extends React.Component {



  render(){
      let{items, currency, handleCurrencySelect, handleTestAxios, totalCost} = this.props
      return (
        <Container className="gridLayout">
            <Row>
                <Col>
                    <h1 className="text-center">Checkout</h1>
                </Col>
            </Row>
            <hr/>
            <Row style={{display:"flex"}}>
                <Col className="Cart-Display" style={{width: '70%'}}>
                    <div id="checkout_page" className="App-page" >
                        {items.map((item, i) => {
                            return <ListedItem item={item} key={i}/>
                        })}
                    </div>
                </Col>
                <Col style={{width:'30%'}}>
                    <h2>Total Cost</h2>
                    <input type="text" readOnly value={totalCost}/>
                    <hr/>
                    <h2>Select Currency</h2>
                    <br/>
                    <select value={currency} onChange={handleCurrencySelect}>
                        <option selected value="USD">USD</option>
                        <option value="CAD">CAD</option>
                        <option value="EUR">EUR</option>
                        <option value="GBP">GBP</option>
                        <option value="JPY">JPY</option>
                        <option value="CNY">CNY</option>
                    </select>

                    <hr/>
                    <Button onClick={handleTestAxios} className="btn btn-default btn-danger">
                        Purchase
                   </Button>
                </Col>
            </Row>
        </Container>
    )
  }
}

export default CheckoutPage