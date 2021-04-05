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
        itemData: [
            {
                title: "test product",
                price: 1.99,
                description: "lorem ipsum set",
                image: "https://i.pravatar.cc",
                category: "electronic"
            }
        ],
        cartData: [
            {
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
            }
        ]
    }

    render() {
        return (
            <div className="App">
                <NavBar />
                <main>
                    <Switch>
                        <Route exact path='/' component={StorePage} />
                        <Route path='/store' component={StorePage} />
                        <Route path='/checkout' component={CheckoutPage} />
                        <Route path='/data' component={DataPage} />
                    </Switch>
                </main>
            </div>
        )
    }
}

export default App