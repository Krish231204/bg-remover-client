import BgRemovalSteps from "../components/BgRemovalSteps";
import Header from "../components/Header";
import Pricing from "../components/Pricing";

const Home = () => {
    return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 font-[Outfit]">

            {/* header section */}
            <Header />

            {/* steps section */}
            <BgRemovalSteps />

            {/* pricing section */}
            <Pricing />
            
            
        </div>
    )
}
export default Home;