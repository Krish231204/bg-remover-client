import { Toaster } from "react-hot-toast";
import Menubar from "./components/Menubar.jsx";
import Home from "./pages/Home.jsx";
import Gallery from "./pages/Gallery.jsx";
import { Route, Routes } from "react-router-dom";


function App() {
  return (
    <div>
      <Menubar />
      <Toaster />
      <Routes>
         <Route path="/" element={<Home />} />
         <Route path="/gallery" element={<Gallery />} />
      </Routes>
    </div>
  );
}
export default App;
