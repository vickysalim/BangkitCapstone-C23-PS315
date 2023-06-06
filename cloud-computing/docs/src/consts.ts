export const SITE = {
  title: "SiFresh API",
  description: "API Documentation for SiFresh Backend",
  defaultLanguage: "en-us",
} as const;

export const OPEN_GRAPH = {
  image: {
    src: "/sifresh.svg",
    alt:
      "SiFresh logo",
  },
  twitter: "",
};

export const KNOWN_LANGUAGES = {
  English: "en",
} as const;
export const KNOWN_LANGUAGE_CODES = Object.values(KNOWN_LANGUAGES);

export const GITHUB_EDIT_URL = `https://github.com/vickysalim/BangkitCapstone-CS23-PS315/tree/main/cloud-computing/docs`;

export const COMMUNITY_INVITE_URL = `#`;

// See "Algolia" section of the README for more information.
export const ALGOLIA = {
  indexName: "XXXXXXXXXX",
  appId: "XXXXXXXXXX",
  apiKey: "XXXXXXXXXX",
};

export type Sidebar = Record<
  (typeof KNOWN_LANGUAGE_CODES)[number],
  Record<string, { text: string; link: string }[]>
>;
export const SIDEBAR: Sidebar = {
  en: {
    "General Information": [
      { text: "Introduction", link: "en/introduction" },
      { text: "Getting Started", link: "en/page-2" },
    ],
    "User Endpoints": [
      { text: "Create User", link: "en/users/create" },
      { text: "Get User Information", link: "en/users/get" },
      { text: "Update User", link: "en/users/update" },
      { text: "Delete User", link: "en/users/delete" },
      { text: "User Authentication", link: "en/users/authentication" },
    ],
    "Product Endpoints": [
      { text: "Create Product", link: "en/product/create" },
      { text: "Get Product Information", link: "en/product/get" },
      { text: "Update Product", link: "en/product/update" },
      { text: "Delete Product", link: "en/product/delete" },
    ],
    "Cart Endpoints": [
      { text: "Create Cart Item", link: "en/cart/create" },
      { text: "Get Cart Item Information", link: "en/cart/get" },
      { text: "Update Cart Item", link: "en/cart/update" },
      { text: "Delete Cart Item", link: "en/cart/delete" },
    ],
    "Order Endpoints": [
      { text: "Create Order", link: "en/order/create" },
      { text: "Get Order Information", link: "en/order/get" },
      { text: "Update Order", link: "en/order/update" },
      { text: "Delete Order", link: "en/order/delete" },
    ],
    "Location Endpoints": [
      { text: "Get Location Information", link: "en/locations/" },
    ],
    "Freshness Dataset": [
      { text: "Get Freshness Information", link: "en/freshness/" },
      { text: "Create New Report", link: "en/freshness/create" },
    ]
  },
};
