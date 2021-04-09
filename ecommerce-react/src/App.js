import React, { Component } from "react";
import { Switch, Route } from "react-router-dom"
// import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css'
import NavBar from "./components/nav_bar"
import CheckoutPage from "./pages/checkout_page";
import StorePage from './pages/store_page'
import DataPage from './pages/data_page'
import LoginPage from './pages/login_page'
import PurchaseHistory from "./pages/PurchaseHistory";
import axios from 'axios';
import UserServiceFetch from './services/UserServiceFetch'
import { Col } from "react-bootstrap";
import Report from "./pages/Report";

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
                quantity: 0,
                itemId: 0,
                category: "fake",
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
        LiveExchangeRate: {
            base: 'USD',
            rates: {
                EUR: '',
                CAD: '',
                JPY: '',
                CNY: '',
                GBP: ''
            },
            date: ''
        },
        currentCurrency: "USD",
        customerId: 1,
        addressId: 0,
        totalCost: "0.00",

        purchaseHistory: [
            {
                purchaseId: '',
                purchaseDate: '',
                currency: '',
                exchange: {
                    exchangeId: '',
                    cad: '',
                    eur: '',
                    gbp: '',
                    jpy: '',
                    cny: ''
                },
                customer: {
                    customerId: '',
                    customerName: '',
                    customerEmail: '',
                    customerPhone: '',
                    address: {
                        addressId: '',
                        street: '',
                        city: '',
                        state: '',
                        postal: '',
                        country: ''
                    }
                },
                items: [
                    {
                        itemId: '',
                        itemName: '',
                        category: '',
                        price: ''
                    }
                ],
                quantities: [
                    {}
                ]
            }
        ],
        totalCostOfPurchases: [{}],
        customerInfo: {
            //customer
            name: "",   // max 50 char
            email: "",  // max 50 char
            phone: "",   // max 10 char and needs regex validation (?)
            //address
            street: "", // max 30 char
            city: "",   // max 30 char
            state: "",  // max 2 char (should be handled by <select> input method)
            postal: "", // max 10 char
            country: "" // max 30 char
        }
    }

    handleCustomerCreation = (newName, newEmail, newPhone,
        newStreet, newCity, newState, newPostal, newCountry) => {
        console.log("test");
            var newCustomer = {
                name: newName,
                email: newEmail,
                phone: newPhone
            }
            var newAddress = {
                street: newStreet,
                city: newCity,
                state: newState,
                postal: newPostal,
                country: newCountry
            }
            this.setState({
                customer: newCustomer,
                address: newAddress
            })
            alert("Customer created!")
            console.log(this.state.customerInfo)
        
    }

    handleCurrencySelect = (event) => {
        let selected = event.target.value;
        this.setState({ currentCurrency: selected })
        this.handleTotalCalculation();

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

    handleAddToCart = (itemTitle, itemPrice, itemQuantity, itemId, itemCategory) => {
        var batch = {
            title: itemTitle,
            price: parseFloat(itemPrice.toFixed(2)),
            quantity: parseInt(itemQuantity),
            itemId: itemId,
            category: itemCategory
        }
        this.state.cartData.push(batch);
        alert("Added to Cart!");
        console.log(this.state.cartData);
    }

    handleTotalCalculation = async () => {
        console.log("test");

        let USDRates = await axios.get('https://api.ratesapi.io/api/latest?base=USD&symbols=CAD,EUR,GBP,JPY,CNY')
            .then(response => response.data.rates);

        this.setState({ exchangeRate: USDRates });

        axios.post('http://localhost:8080/cart/findTotal',
            {
                cart: this.state.cartData,
                currency: this.state.currentCurrency,
                exchange: this.state.exchangeRate
            }).then(response =>
                this.setState({ cartData: response.data }));
    }

    handleTestAxios = async (event) => {

        let USDRates = await axios.get('https://api.ratesapi.io/api/latest?base=USD&symbols=CAD,EUR,GBP,JPY,CNY')
            .then(response => response.data.rates)

        this.setState({ exchangeRate: USDRates });

        axios.post('http://localhost:8080/cart/makePurchase',
            {
                purchaseDate: null,
                currency: this.state.currentCurrency,
                exchange: this.state.exchangeRate,
                addressId: this.state.addressId,
                customerId: this.state.customerId,
                cartData: this.state.cartData
            }).then(response =>
                this.setState({ totalCost: parseFloat(response.data.totalCost).toFixed(2) })
            );
    }
    //mount all the data
    componentDidMount() {
        console.log("App is now mounted.")
        this.loadItemData();
        this.loadPurchaseHistory();
        this.loadExchangeRate();
        this.loadPurchaseTotalCost();
        this.state.cartData.pop();
        console.log(this.state.customerInfo)
    }
    //retrieve current exchange rate
    loadExchangeRate() {
        axios.get('https://api.ratesapi.io/api/latest?base=USD&symbols=CAD,EUR,GBP,JPY,CNY').then(
            ((res) => this.setState({ LiveExchangeRate: res.data })
            ))
    }
    //retrieve purchase history from database
    loadPurchaseHistory() {
        axios.get('http://localhost:8080/cart/history').then((res) =>
            this.setState({ purchaseHistory: res.data }))
    }
    //retrieve total cost for each purchase from service layer
    loadPurchaseTotalCost() {
        axios.get('http://localhost:8080/cart/totals').then((res) =>
            this.setState({ totalCostOfPurchases: res.data }))
    }
    //set items, current exchange rates and purchase history
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
                            selectCustomer={this.handleCustomerSelect} selectAddress={this.handleAddressSelect}
                            handleSubmit={this.handleCustomerCreation}/>)}
                        />
                        <Route path='/store' render={props =>
                        (<StorePage items={this.state.itemData} handleSelect={this.selectHandler}
                            handleAdd={this.handleAddToCart} cart={this.state.cartData} />)}
                        />
                        <Route path='/checkout' render={props =>
                        (<CheckoutPage items={this.state.cartData} currency={this.state.currentCurrency}
                            handleCurrencySelect={this.handleCurrencySelect} handleTestAxios={this.handleTestAxios}
                            totalCost={this.state.totalCost} handleTotalCalculation={this.handleTotalCalculation}
                            exchangeRate={this.state.LiveExchangeRate} />)}
                        />
                        <Route path='/history' render={props => (<PurchaseHistory
                            purchaseHistory={this.state.purchaseHistory}
                            totalCostOfPurchases={this.state.totalCostOfPurchases}
                        />)}
                        />
                        <Route path='/report' render={props =>
                            <Report purchaseHistory={this.state.purchaseHistory}
                                currency={this.state.currentCurrency}
                                handleCurrencySelect={this.handleCurrencySelect}
                                totalCostOfPurchases={this.state.totalCostOfPurchases}
                            />} />
                    </Switch>
                </main>
            </div>
        )
    }
}

export default App
