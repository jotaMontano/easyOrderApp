// These constants are injected via webpack environment variables.
// You can add more variables in webpack.common.js or in profile specific webpack.<dev|prod>.js files.
// If you change the values in the webpack config files, you need to re run webpack to update the application

export const VERSION = process.env.VERSION;
export const DEBUG_INFO_ENABLED: boolean = !!process.env.DEBUG_INFO_ENABLED;
export const SERVER_API_URL = process.env.SERVER_API_URL;
export const BUILD_TIMESTAMP = process.env.BUILD_TIMESTAMP;
export const FIREBASE = {
    production: false,
    config: {
        apiKey: 'AIzaSyB-NXhZvZJqnuMJbfgnpL8UjLqSBKiE7ek',
        authDomain: 'easyorder-50ff9.firebaseapp.com',
        databaseURL: 'https://easyorder-50ff9.firebaseio.com',
        projectId: 'easyorder-50ff9',
        storageBucket: 'easyorder-50ff9.appspot.com',
        messagingSenderId: '604638177714'
    }
};