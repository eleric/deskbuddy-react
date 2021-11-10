import React from "react";
import ScrollPhotos from "./ScrollPhotos"
import Header from "./Header"
import ListPhotos from "./ListPhotos";
import AddPhoto from "./AddPhoto";
import Home from "./Home";
import { v4 as uuidv4} from "uuid";
import { Routes, Route, Switch } from "react-router-dom"
import NotFound from "./NotFound";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";

const fetchData = (props) => {
    return fetch("/photos/eleric")
          .then((response) => response.json())
          .then((json) => {
            // console.log(json);
            let ary = [];
            json.forEach(j => {
                ary = [...ary, {
                    id: uuidv4(),
                    name: j.name,
                    location: j.location,
                    marked: false
                }];
            });
            let photos = {
                photos: ary
            }
            // console.log(photos);
            props.initStateProps(photos);
      });
};

const Retrievedata = (props) => {
    useEffect(() => {
        fetchData(props);
    }, [] )
    return null // component does not render anything
  }

class DeskBuddyContainer extends React.Component {

    state = {
        photos: [ ]
    };
    initState = (ary) => {
        this.setState(prevState => (ary));
    };
    handleChange = (id)=> {
        this.setState(prevState => ({
            photos: prevState.photos.map(photo => {
                if (photo.id === id) {
                    return {
                        ...photo,
                        marked: !photo.marked
                    }
                    photo.marked = !photo.marked;
                }
                return photo
            })
        }));
    };
    delPhoto = photos => {
        this.setState(
            {
                photos: [
                    ...photos.filter(p => {
                        return !p.marked;
                    })
                ]
            }
        )
        
    };
    addPhoto = photoName => {
        const newPhoto = {
            id: uuidv4(),
            name: photoName,
            marked: false
        }
        this.setState({
            photos: [...this.state.photos, newPhoto]
        })
    };
    homeLink = <Link to="/">Home</Link>;

    render() {

        return (
                <Routes>
                    <Route path="/" element={
                        <div>
                            <Retrievedata initStateProps={this.initState} />
                            <Header funct="Home" />
                            <Home />
                        </div>
                    } />
                    <Route path="/edit" element={
                        <div>
                            <Retrievedata initStateProps={this.initState} />
                            <Header funct="Edit Photos" />
                            <AddPhoto handleAddProps={this.addPhoto} />
                            <ListPhotos photos={this.state.photos} handleChangeProps={this.handleChange} handleDeleteProps={this.delPhoto} />
                            {this.homeLink}
                        </div>
                    } />
                    <Route path="/scroll" element={
                        <div>
                            <Retrievedata initStateProps={this.initState} />
                            <ScrollPhotos photos={this.state.photos} />
                            {this.homeLink}
                        </div>
                    } />
                    <Route path="*" element={
                        <div>
                            <Header funct="Page Not Found" />
                            <NotFound />
                        </div>
                    } />
                </Routes>
        )
    }
}
export default DeskBuddyContainer;