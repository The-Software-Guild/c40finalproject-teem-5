import React, { Component } from "react";
import { Switch, Route } from "react-router-dom"
import './App.css'
import NavBar from "./components/nav_bar"
import CheckoutPage from "./pages/checkout_page";
import StorePage from './pages/store_page'
import DataPage from './pages/data_page'
import LoginPage from './pages/login_page'
import axios from 'axios';
import UserServiceFetch from './services/UserServiceFetch'
import { Col } from "react-bootstrap";

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
                quantity: 5,
                itemId:0,
                category:'fake',
                totalForCard: "0.00"                  
            }
        ],
        exchangeRate: {
            CAD: 1.1234,
            EUR: 1.1234,
            GBP: 1.1234,
            JPY: 1.1234,
            CNY: 1.1234
        },
        currentCurrency: "USD",
        customerId: 1,
        addressId: 0,
        totalCost:"0.00",
        
    }

    

    handleCurrencySelect = (event) => {
        let selected = event.target.value;
        this.setState({ currentCurrency: selected })

    }

    handleCustomerSelect = (event) => {
        let selected = event.target.value;
        this.setState({ customerId: selected })
        console.log(selected)
    }


    handleAddressSelect = (event) => {
        let selected = event.target.value;
        this.setState({ addressId: selected })
        console.log(selected)
    }


    handleTotalCalculation = async() =>{
        console.log("test");

        let USDRates = await axios.get('https://api.ratesapi.io/api/latest?base=USD&symbols=CAD,EUR,GBP,JPY,CNY')
                    .then(response => response.data.rates);

       this.setState({exchangeRate:USDRates});

       axios.post('http://localhost:8080/cart/findTotal',
       {
           cart:this.state.cartData,
           currency:this.state.currentCurrency,
           exchange:this.state.exchangeRate
       });
    }
    

    handleTestAxios = async(event) =>{

        let USDRates = await axios.get('https://api.ratesapi.io/api/latest?base=USD&symbols=CAD,EUR,GBP,JPY,CNY')
                    .then(response => response.data.rates)

       this.setState({exchangeRate:USDRates});

            axios.post('http://localhost:8080/cart/makePurchase',
            {
                purchaseDate:null,
                currency:this.state.currentCurrency,
                exchange:this.state.exchangeRate,
                addressId:this.state.addressId,
                customerId:this.state.customerId,
                cartData:this.state.cartData
            }).then(response =>
            this.setState({totalCost:parseFloat(response.data.totalCost).toFixed(2)})
            );
        
    }


    componentDidMount() {
        console.log("App is now mounted.")
        this.loadItemData();
        this.handleTotalCalculation();
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
                        <Route exact path='/' render={props =>
                        (<LoginPage customer={this.state.customerId} address={this.state.addressId}
                            selectCustomer={this.handleCustomerSelect} selectAddress={this.handleAddressSelect} />)}
                        />
                        <Route path='/store' render={props =>
                            (<StorePage items={this.state.itemData} />)}
                        />
                        <Route path='/checkout' render={props =>
                        (<CheckoutPage items={this.state.cartData} currency={this.state.currentCurrency}
                            handleCurrencySelect={this.handleCurrencySelect} handleTestAxios={this.handleTestAxios}
                            totalCost={this.state.totalCost} handleTotalCalculation={this.handleTotalCalculation}/>)}
                        />

                        <Route path='/data' component={DataPage} />
                    </Switch>
                </main>
            </div>
        )
    }
}

export default App