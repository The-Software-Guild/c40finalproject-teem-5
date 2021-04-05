import React, { Component } from "react"
import '../styles/store_page.css'
import { Row, Container, Card, CardDeck, Button, CardColumns } from 'react-bootstrap'

const ItemCard = ({ item }) => {
    return (
        <div>
            <Card style={{ maxHeight:"400px", maxWidth: "250px", borderStyle: "outset", padding: "5px" }}>
                <Card.Body>
                    <Card.Title style={{ fontSize: "14px" }}>
                        {item.title}
                        <br />
                        {item.price.toFixed(2)}
                    </Card.Title>
                    <Card.Img src={item.image} style={{ height: "100px" }} />
                    <Card.Text style={{ maxHeight:"80px", fontSize: "14px", overflow:"scroll", overflowX:"hidden"}}>
                        {item.description}
                    </Card.Text>
                    <Button>Add to Cart</Button>
                </Card.Body>
            </Card>
        </div>
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
            <div>
                <Container>
                    <CardColumns>
                        {items.map((item, i) => {
                            return <ItemCard item={item} key={i} />
                        })}
                    </CardColumns>
                </Container>
            </div>
        )
    }
}

export default StorePage