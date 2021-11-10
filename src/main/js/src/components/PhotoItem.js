import React from "react";


function PhotoItem(props) {
    
    return <li key={props.photo.id}>
        <input 
        type='checkbox' 
        checked={props.photo.marked} 
        onChange={() => props.handleChangeProps(props.photo.id)}
        /> {props.photo.name}
        </li>
}

export default PhotoItem;