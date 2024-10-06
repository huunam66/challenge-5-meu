/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {
      colors: {
        colorHeader: "#21316c",
        colorBackground: "#f9f9fc",
        colorPresentNavLink: "#ffa117",
        colorNavLinkText: "#fafafc",
        "white-opa01": "rgba(255, 255, 255, 0.1)",
        colorBackgroundAuth: '#e2e8f0',
        colorFormAuth: '#fefefe',
        colorButtonAuth: '#319795'
      },
      gridTemplateColumns: {
        columnsTable: "50px 440px 1fr 80px",
      },
      fontFamily: {
        "fira-sans": ["Fira Sans", "sans-serif"],
      },
    },
  },
  plugins: [],
};
