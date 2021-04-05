import axios from 'axios';

class UserServiceFetch{
    addPurchase(){
        return axios.post('http://localhost:8080/cart/makePurchase',{
            purchaseID:75,
            purchaseDate:null,
            currency:"USD",
            exchange:null,
            customer:null,
            items:null
        }).then(response => response.json)
    }
}

export default new UserServiceFetch();