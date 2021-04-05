import React from "react"
import { NavLink } from 'react-router-dom'
import "../styles/nav_bar.css"

function NavBar() {
    return (
        <div className="nav_list">
            <ul>
                <li><NavLink to='/store'>Store</NavLink></li>
                <li><NavLink to='/checkout'>Checkout</NavLink></li>
                <li><NavLink to='/data'>Data</NavLink></li>
            </ul>
        </div>
    );
}

export default NavBar