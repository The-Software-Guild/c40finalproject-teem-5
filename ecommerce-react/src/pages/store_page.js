import React, { Component } from "react"
import { Row, Container, Card, CardColumns } from 'react-bootstrap';

const ItemCard = ({ item, index }) => {
    return (
        <Card bg="primary" text="white" style={{ minHeight: "200px", minWidth: "500px" , position: "relative"}}>
            <Card.Body>
                <Card.Img src={item.image} style={{ height: "100px" }} />
                <Card.Title>{item.title}</Card.Title>
                <Card.Text>
                    {item.price}
                    <br />
                    {item.description}
                </Card.Text>
                <Card.Footer>{item.category}</Card.Footer>
            </Card.Body>
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
            <div id="store_page" className="App-page"
            >
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