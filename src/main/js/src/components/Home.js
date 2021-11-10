import React from "react";
import { Link } from "react-router-dom";

function Home(props) {
    const links = [
        {
            id: 1,
            path: "/scroll",
            descr: "Scroll Photos"
        },
        {
            id: 2,
            path: "/edit",
            descr: "Edit Photos"
        },
        
    ]
    
    return <div>
        <ul>
            {links.map(l => {
                return <li key={l.id}>
                        <Link to={l.path}>{l.descr}</Link>
                    </li>
            })}
        </ul>
    </div>
}

export default Home;