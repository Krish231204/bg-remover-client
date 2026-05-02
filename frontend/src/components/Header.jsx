import toast from "react-hot-toast";
import { assets } from "../assets/assets";
import { motion } from "framer-motion";
import { useRef, useState } from "react";
import { Download } from "lucide-react";
import { useUser } from "@clerk/clerk-react";
import API_BASE_URL from "../config";

const Header = () =>{
    const inputRef = useRef(null);
    const [processedImage, setProcessedImage] = useState(null);
    const { user } = useUser();

    const handleFileChange = async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        const toastId = toast.loading("Processing image...");

        try {
            const formData = new FormData();
            formData.append("image", file);
            if (user) {
                formData.append("userId", user.id);
            }

            const response = await fetch(`${API_BASE_URL}/api/bgremover/remove`, {
                method: "POST",
                body: formData,
            });

            const data = await response.json();

            if (data.success) {
                toast.success("Image processed successfully!", { id: toastId });
                setProcessedImage(data.data);
                console.log("Processed image:", data.data);
            } else {
                toast.error(data.data || "Failed to process image", { id: toastId });
                setProcessedImage(null);
            }
        } catch (error) {
            toast.error("Upload failed: " + error.message, { id: toastId });
            console.error("Upload error:", error);
            setProcessedImage(null);
        }
    };

    const downloadImage = () => {
        if (!processedImage) return;
        
        const link = document.createElement("a");
        link.href = `${API_BASE_URL}/api${processedImage}`;
        link.download = "processed-image.png";
        link.click();
    };

    return(
        <div>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-12 items-center mb-16">
                {/* left side */}
                <div className="order-1 md:order-1 flex justify-center">
                    {processedImage ? (
                        <motion.div
                            initial={{ opacity: 0, scale: 0.8 }}
                            animate={{ opacity: 1, scale: 1 }}
                            className="shadow-[0_50px_50px_-12px_rgba(0,0,0,0.15)] rounded-4xl overflow-hidden"
                        >
                            <img 
                                src={`${API_BASE_URL}/api${processedImage}`}
                                alt="Processed"
                                className="w-full max-w-[400px] h-auto object-cover"
                            />
                        </motion.div>
                    ) : (
                        <div className="shadow[0_50px_50px_-12px_rgba(0,0,0,0.15)] rounded-4xl overflow-hidden">
                            <video src={assets.video_banner} autoPlay loop muted className="w-full max-w-[400] h-auto object-cover" />
                        </div>
                    )}
                </div>
                

                {/* right side */}
                <div className="order-1 md:order-2">
                    <h1 className="text-4xl md:text-5xl font-bold text-gray-900 mb-6 leading-tight">
                        <span className="text-gray-900">
                            {"Background.".split("").map((char, index) => (
                                <motion.span
                                    key={index}
                                    initial={{ opacity: 0 }}
                                    animate={{ opacity: 1 }}
                                    transition={{ duration: 0.05, delay: index * 0.05 }}
                                >
                                    {char}
                                </motion.span>
                            ))}
                        </span>
                        <span className="text-indigo-700">
                            {"Removed.".split("").map((char, index) => (
                                <motion.span
                                    key={index}
                                    initial={{ opacity: 0 }}
                                    animate={{ opacity: 1 }}
                                    transition={{ duration: 0.05, delay: 0.55 + index * 0.05 }}
                                >
                                    {char}
                                </motion.span>
                            ))}
                        </span><br />
                        <span className="text-gray-900">
                            {"Beautifully.".split("").map((char, index) => (
                                <motion.span
                                    key={index}
                                    initial={{ opacity: 0 }}
                                    animate={{ opacity: 1 }}
                                    transition={{ duration: 0.05, delay: 1.1 + index * 0.05 }}
                                >
                                    {char}
                                </motion.span>
                            ))}
                        </span>
                    </h1>
                

                <motion.p className="text-gray-600 mb-8 text-lg leading-relaxed"
                    initial={{ opacity: 0 }}
                    animate={{ opacity: 1 }}
                    transition={{ delay: 1.8, duration: 1 }}
                >
                    Remove image backgrounds effortlessly in seconds.
                    Powered by smart AI to deliver clean, precise cutouts every time.
                    Fast, reliable, and designed to make your visuals stand out.
                </motion.p>

                
                
                <motion.div
                    initial={{ opacity: 0 }}
                    animate={{ opacity: 1 }}
                    transition={{ delay: 2.2, duration: 1 }}
                >
                    <input 
                        type="file" 
                        accept="image/*" 
                        id="upload1" 
                        hidden 
                        ref={inputRef}
                        onChange={handleFileChange}
                    />
                    <label htmlFor="upload1" className="bg-black text-white font-medium px-8 py-4 rounded-full hover:opacity-90 transition-transform hover:scale-105 text-lg cursor-pointer inline-block mr-4">
                        Upload Image
                    </label>
                    
                    {processedImage && (
                        <button
                            onClick={downloadImage}
                            className="bg-indigo-700 text-white font-medium px-8 py-4 rounded-full hover:opacity-90 transition-transform hover:scale-105 text-lg inline-flex items-center gap-2"
                        >
                            <Download size={20} />
                            Download
                        </button>
                    )}
                </motion.div>

                </div>

            </div>
        </div>
    )
}
export default Header;
