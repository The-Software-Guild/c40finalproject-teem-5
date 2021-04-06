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
            USD: 1.1234,
            CAD: 1.1234,
            EUR: 1.1234,
            GBP: 1.1234,
            JPY: 1.1234,
        },
        currentCurrency:"USD"
    }

    handleCurrencySelect = (event) =>{
        let selected = event.target.value;
        this.setState({currentCurrency:selected})
    }



    handleTestAxios = async(event) =>{

        let USDRate = await axios.get('http://data.fixer.io/api/latest?access_key=5577025857c2f2cc601f6bd524482428')
            .then(response =>
            response.data.rates.USD);

        let CADRate = await axios.get('http://data.fixer.io/api/latest?access_key=5577025857c2f2cc601f6bd524482428')
            .then(response =>
            response.data.rates.CAD);

        let EURRate = await axios.get('http://data.fixer.io/api/latest?access_key=5577025857c2f2cc601f6bd524482428')
            .then(response =>
            response.data.rates.EUR);

        let GBPRate = await axios.get('http://data.fixer.io/api/latest?access_key=5577025857c2f2cc601f6bd524482428')
            .then(response =>
            response.data.rates.GBP);

        let JPYRate = await axios.get('http://data.fixer.io/api/latest?access_key=5577025857c2f2cc601f6bd524482428')
            .then(response =>
            response.data.rates.JPY);

        var rates = {
            USD:USDRate,
            CAD:CADRate,
            EUR:EURRate,
            GBP:GBPRate,
            JPY:JPYRate
        }
        console.log(rates);

        this.setState({exchangeRate:rates});

        console.log(this.state.exchangeRate);

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