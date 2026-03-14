import { useEffect, useState } from "react";
import { useUser } from "@clerk/clerk-react";
import { Trash2, Download } from "lucide-react";
import toast from "react-hot-toast";
import { motion } from "framer-motion";

const Gallery = () => {
    const { user, isLoaded } = useUser();
    const [images, setImages] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!isLoaded || !user) {
            setLoading(false);
            return;
        }

        fetchUserImages();
    }, [user, isLoaded]);

    const fetchUserImages = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/images/history/${user.id}`);
            const data = await response.json();
            setImages(data);
        } catch (error) {
            toast.error("Failed to load image history");
            console.error("Error fetching images:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (imageId) => {
        if (!confirm("Are you sure you want to delete this image?")) return;

        try {
            const response = await fetch(
                `http://localhost:8080/api/images/${imageId}/${user.id}`,
                { method: "DELETE" }
            );

            if (response.ok) {
                toast.success("Image deleted");
                setImages(images.filter(img => img.id !== imageId));
            } else {
                toast.error("Failed to delete image");
            }
        } catch (error) {
            toast.error("Error deleting image");
            console.error("Delete error:", error);
        }
    };

    const handleDownload = (imagePath) => {
        const link = document.createElement("a");
        link.href = `http://localhost:8080/api${imagePath}`;
        link.download = `processed-${Date.now()}.png`;
        link.click();
    };

    if (!isLoaded || !user) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <p className="text-xl text-gray-500">Please sign in to view your image history</p>
            </div>
        );
    }

    if (loading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <p className="text-xl text-gray-500">Loading...</p>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-7xl mx-auto">
                <h1 className="text-4xl font-bold text-gray-900 mb-2">Your Image History</h1>
                <p className="text-gray-600 mb-8">
                    {images.length} image{images.length !== 1 ? "s" : ""} processed
                </p>

                {images.length === 0 ? (
                    <div className="text-center py-12">
                        <p className="text-gray-500 text-lg">
                            No processed images yet. Upload one to get started!
                        </p>
                    </div>
                ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {images.map((image, index) => (
                            <motion.div
                                key={image.id}
                                initial={{ opacity: 0, y: 20 }}
                                animate={{ opacity: 1, y: 0 }}
                                transition={{ delay: index * 0.1 }}
                                className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow"
                            >
                                <img
                                    src={`http://localhost:8080/api${image.processedImagePath}`}
                                    alt={image.originalFileName}
                                    className="w-full h-48 object-cover"
                                />
                                <div className="p-4">
                                    <h3 className="font-semibold text-gray-900 truncate">
                                        {image.originalFileName}
                                    </h3>
                                    <p className="text-sm text-gray-500 mb-4">
                                        {new Date(image.createdAt).toLocaleDateString()}
                                    </p>
                                    <div className="flex gap-2">
                                        <button
                                            onClick={() => handleDownload(image.processedImagePath)}
                                            className="flex-1 flex items-center justify-center gap-2 bg-indigo-700 text-white px-4 py-2 rounded-lg hover:bg-indigo-800 transition-colors"
                                        >
                                            <Download size={18} />
                                            Download
                                        </button>
                                        <button
                                            onClick={() => handleDelete(image.id)}
                                            className="flex items-center justify-center gap-2 bg-red-100 text-red-700 px-4 py-2 rounded-lg hover:bg-red-200 transition-colors"
                                        >
                                            <Trash2 size={18} />
                                        </button>
                                    </div>
                                </div>
                            </motion.div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default Gallery;
