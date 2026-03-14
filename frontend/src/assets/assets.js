import logo from './logo.png';
import video_banner from './home-page-banner.mp4';

export const assets = {
    logo,
    video_banner,
}

export const steps = [
    {
        step: "Step 1",
        title: "Select Image",
        description: 'First choose an image from your device that you want to edit. Your image format can be JPG or PNG. We support all image dimensions.'
            
    },

    {
        step: "Step 2",
        title: "Background Removal",
        description: 'Our AI-powered tool will automatically detect and remove the background from your image. You can choose your background color, the most popular options are white and transparent.'
    
    },

    {
        step: "Step 3",
        title: "Download Image",
        description: 'Once the background is removed, you can download the image in your device. You can also save the image in your photoroom app by creating an account.'
           
    },
];

export const plans = [
    {
        id: "Basic",
        name: "Basic Plan",
        price: 100,
        credits: "100 credits",
        description: "Best for personal use",
        popular: false
    },

    {
        id: "Premium",
        name: "Premium Plan",
        price: 250,
        credits: "250 credits",
        description: "Best for business use",
        popular: true
    },

    {
        id: "Pro",
        name: "Pro plan",
        price: 500,
        credits: "500 credits",
        description: "Best for enterprise use",
        popular: false
    }
];