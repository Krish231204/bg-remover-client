import { useState } from "react";
import { assets } from "../assets/assets.js";
import { Menu, X } from "lucide-react";
import { Link } from "react-router-dom";
import { SignInButton, SignUpButton, SignedIn, SignedOut, UserButton } from "@clerk/clerk-react";

const Menubar = () => {
    const [menuOpen, setMenuOpen] = useState(false);

    return(
        <nav className = "bg-white px-8 py-4 flex justify-between items-center">

            <Link className= "flex items-center space-x-2" to ="/" >
                <img src={assets.logo} alt="logo" className= "w-8 h-8 object-contain cursor-pointer"/>
                <span className= "text-2xl font-semibold text-indigo-700 cursor-pointer">
                    bg.<span className= "text-gray-400 cursor-pointer">remover</span>
                </span>
            </Link>

            <div className="hidden md:flex items-center space-x-4">
                <a href="#steps" className="text-gray-700 hover:text-blue-500 font-medium">How it works</a>
                <a href="#pricing" className="text-gray-700 hover:text-blue-500 font-medium">Pricing</a>
                <SignedIn>
                    <Link to="/gallery" className="text-gray-700 hover:text-blue-500 font-medium">
                        Gallery
                    </Link>
                </SignedIn>
                <SignedOut>
                    <SignInButton mode="modal">
                        <button className="text-gray-700 hover:text-blue-500 font-medium">
                            Login
                        </button>
                    </SignInButton>
                    <SignUpButton mode="modal">
                        <button className="bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium px-4 py-2 rounded-full transition-all">
                            Sign up
                        </button>
                    </SignUpButton>
                </SignedOut>
                <SignedIn>
                    <UserButton afterSignOutUrl="/" />
                </SignedIn>
            </div>

            {/* mobile view */}
            <div className= "flex md:hidden">
                <button onClick={() => setMenuOpen(!menuOpen)}>
                    {menuOpen ? <X size={28}/> : <Menu size={28}/>}
                </button>
            </div>

            {menuOpen && (
                <div className="absolute top-16 right-8 bg-white shadow-md rounded-md flex flex-col space-y-4 w-40 p-4">
                    <SignedOut>
                        <SignInButton mode="modal">
                            <button className="text-gray-700 hover:text-blue-500 font-medium">
                                Login
                            </button>
                        </SignInButton>
                        <SignUpButton mode="modal">
                            <button className="bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium px-4 py-2 rounded-full text-center">
                                Sign up
                            </button>
                        </SignUpButton>
                    </SignedOut>
                    <SignedIn>
                        <UserButton afterSignOutUrl="/" />
                    </SignedIn>
                </div>
            )}


        </nav>
    )
}

export default Menubar;