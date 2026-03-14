/**
 * API Configuration
 * Configure API endpoints for backend communication
 */

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

export const API_ENDPOINTS = {
  // User endpoints
  USER: {
    REGISTER: `${API_BASE_URL}/api/users/register`,
    LOGIN: `${API_BASE_URL}/api/users/login`,
    PROFILE: `${API_BASE_URL}/api/users/profile`,
  },
  // Background removal endpoints
  BG_REMOVAL: {
    REMOVE: `${API_BASE_URL}/api/bgremover/remove`,
    UPLOAD: `${API_BASE_URL}/api/bgremover/upload`,
  },
};

export default API_BASE_URL;
