import React from "react";
import '../index.css';
import { useRef } from "react";

// const colors = ["#0088FE", "#00C49F", "#FFBB28"];
const delay = 2500;

function ScrollPhotos(props) {
  const [index, setIndex] = React.useState(0);
  const [location, setLocation] = React.useState(props.photos[index] == null ? "" : props.photos[index].location);
  const myContainer = useRef(null);

  React.useEffect(() => {
    if (props.photos[index] != null && props.photos.length > 0){
      setLocation((prevLocation) => {
        return props.photos[index].location;
      })
    }

    return () => {};
  }, [props.photos])

  React.useEffect(() => {

    setTimeout(
      () =>
      {
        let i = setIndex((prevIndex) => {
          return prevIndex === props.photos.length - 1 ? 0 : prevIndex + 1
        }
        )
        if (props.photos[index] != null){
          setLocation((prevLocation) => {
            return props.photos[index].location;
          })
        }
        return i;
      }
        ,
      delay
    );
    return () => {};
  }, [index]);

        return(
          <div>
            <img  className="slide" id="photo" key={index} src={location} />
          </div>
        )
}

export default ScrollPhotos;