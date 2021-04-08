import React from 'react';
import { Card, Button, Container, Row, Col } from 'react-bootstrap';
import axios from 'axios';


const ListedItem = ({item, key }) =>{

    return(

        <Card style={{width: '18rem', backgroundColor:"lightblue"}} >
            <Card.Body>
                <Card.Title>{item.title}</Card.Title>
                <div>
                    <br/>
                    Price: {item.price}
                    <br/>
                    Quantity: {item.quantity}
                    <br/>
                    <br/>
                    <br/>
                </div>
            </Card.Body>
        </Card>

    );
}

class CheckoutPage extends React.Component {



  render(){
      let{items, currency, handleCurrencySelect, handleTestAxios} = this.props
      return (
        <Container fluid>
            <Row>
                <Col>
                    <h1 className="text-center">Checkout</h1>
                </Col>
            </Row>
            <hr/>
            <Row style={{display:"flex", justifyContent:"space-between"}}>
                <Col sm={8} className="Cart-Display">
                    <span id="checkout_page" className="App-page">
                        {items.map((item, i) => {
                            return <ListedItem item={item} key={i} />
                        })}
                    </span>
                </Col>
                <Col sm={4}>
                    <input type="text" defaultValue="Deposit Money"/>
                    <hr/>
                    <span>Select Currency</span>
                    <br/>
                    <select value={currency} onChange={handleCurrencySelect}>
                        <option selected value="USD">USD</option>
                        <option value="CAD">CAD</option>
                        <option value="EUR">EUR</option>
                        <option value="GBP">GBP</option>
                        <option value="JPY">JPY</option>
                    </select>

                    <hr/>
                    <Button onClick={handleTestAxios}>
                        Purchase
                   </Button>
                   <hr/>
                </Col>
            </Row>
        </Container>
    )
  }
}

export default CheckoutPage