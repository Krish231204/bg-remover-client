import { steps } from '../assets/assets.js';
import { motion } from "framer-motion";

const BgRemovalSteps = () => {
    return (
        <div id="steps" className="text-center mb-16">

            <motion.h2 
                className="text-4xl md:text-4x1 font-bold text-gray-900 mb-12"
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.5 }}
            >
                How to Remove Background?
            </motion.h2>

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">

                {steps.map((item, index) => (
                    <motion.div 
                        key={index} 
                        className="bg-gray-50 p-8 rounded-2x1 shadow-sm"
                        initial={{ opacity: 0, y: 50 }}
                        whileInView={{ opacity: 1, y: 0 }}
                        viewport={{ once: true }}
                        transition={{ duration: 0.5, delay: index * 0.2 }}
                        whileHover={{ scale: 1.05 }}
                    >

                        <span className="inline-block bg-gray-200 text-indigo-800 text-sm font-semibold px-3 py-1 rounded-full mb-4">
                            {item.step}
                        </span>
                        <h3 className="text-xl font-bold text-gray-900 mb-4">
                            {item.title}
                        </h3>
                        <p className="text-gray-600 text-base leading-relaxed">
                            {item.description}
                        </p>

                    </motion.div>

                ))}



            </div>
     
            
        </div>
    )
}
export default BgRemovalSteps;