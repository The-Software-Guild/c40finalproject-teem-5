import React, { Component } from "react"
import '../styles/store_page.css'
import { Row, Container, Card, CardColumns, Button } from 'react-bootstrap'

const ItemCard = ({ item, index }) => {
    return (
        <Card style={{ maxWidth: "200px", borderStyle: "outset", padding:"5px"}}>
            <Card.Body>
                <Card.Img src={item.image} style={{ height: "100px" }} />
                <Card.Title style={{ fontSize: "10px" }}>{item.title}</Card.Title>
                <Card.Text style={{ fontSize: "10px" }}>
                    {item.price.toFixed(2)}
                    <br />
                    {item.description}
                </Card.Text>
                <Button>Add to Cart</Button>
                </Card.Body>
                <Card.Footer style={{ fontSize: "10px" }}>{item.category}</Card.Footer>
        </Card>
    );
}

class StorePage extends Component {

    static defaultsProps = {
        items: [{
            id: 0,
            title: "test product",
            price: 1.99,
            description: "lorem ipsum set",
            image: "https://i.pravatar.cc",
            category: "electronic"
        }]
    }

    render() {
        let { items } = this.props;
        return (
            <div id="store_page" className="Store-page" style={{ flexDirection: "row" }}>
                <div> Storefront </div>
                            {items.map((item, i) => {
                                return <ItemCard item={item} key={i} />
                            })}
            </div>
        )
    }
}

export default StorePage