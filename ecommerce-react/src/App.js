import React, { Component } from "react";
import { Switch, Route } from "react-router-dom"
import './App.css'
import NavBar from "./components/nav_bar"
import CheckoutPage from "./pages/checkout_page";
import StorePage from './pages/store_page'
import DataPage from './pages/data_page'
import axios from 'axios';
import UserServiceFetch from './services/UserServiceFetch'

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
                quantity: 0,
                customerId:0,
                addressId:0
            }
        ],
        exchangeRate: {
            CAD: 1.1234,
            EUR: 1.1234,
            GBP: 1.1234,
            JPY: 1.1234,
            CNY: 1.1234
        },
        currentCurrency:"USD"
    }

    handleCurrencySelect = (event) =>{
        let selected = event.target.value;
        this.setState({currentCurrency:selected})
    }

    handleTestAxios = (event) =>{

        UserServiceFetch.addPurchase().then((response =>
            console.log(response)
        ));

    }

    render() {
        return (
            <div className="App">
                <NavBar />
                <main>
                    <Switch>
                        <Route exact path='/' component={StorePage} />
                        <Route path='/store' component={StorePage} />
                        <Route path='/checkout' render={props =>
                        (<CheckoutPage items={this.state.cartData} currency={this.state.currentCurrency} handleCurrencySelect={this.handleCurrencySelect} handleTestAxios={this.handleTestAxios}/>)}/>

                        <Route path='/data' component={DataPage}/>
                    </Switch>
                </main>
            </div>
        )
    }
}

export default App