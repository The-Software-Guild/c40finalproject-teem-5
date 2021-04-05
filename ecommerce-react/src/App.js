import React, { Component } from "react";
import { Switch, Route } from "react-router-dom"
import './App.css'
import NavBar from "./components/nav_bar"
import CheckoutPage from "./pages/checkout_page";
import StorePage from './pages/store_page'
import DataPage from './pages/data_page'

const STORE_URL = "https://fakestoreapi.com"
const EXCHANGE_RATE_URL = "http://data.fixer.io/api"

class App extends Component {
    state = {
        userId: null,
        itemData: [{
            id: null,
            title: "test product",
            price: 1.99,
            description: "lorem ipsum set",
            image: "https://i.pravatar.cc",
            category: "electronic"
        }],
        cartData: [{
            title: "test product",
            price: 1.99,
            exchangeRate: {
                USD: 1.1234,
                CAD: 1.1234,
                EUR: 1.1234,
                GBP: 1.1234,
                JPY: 1.1234,
            },
            quantity: 0
        }]
    }

    componentDidMount() {
        console.log("App is now mounted.")
        this.loadItemData();
    }

    loadItemData() {
        this.setState({ loading: true })
        console.log("Loading item data")
        fetch(STORE_URL + "/products")
            .then(data => data.json())
            .then(data => this.setState(
                { itemData: data, loading: false }
            ))
    }

    render() {
        return (
            <div className="App">
                <NavBar />
                <main>
                    <Switch>
                        <Route path='/store' render={props =>
                            (<StorePage items={this.state.itemData} />)}
                        />
                        <Route path='/checkout' render={props =>
                            (<CheckoutPage items={this.state.itemData} />)} />
                        <Route path='/data' component={DataPage} />
                    </Switch>
                </main>
            </div>
        )
    }
}

export default App