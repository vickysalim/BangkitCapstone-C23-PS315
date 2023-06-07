---
title: "Getting Started"
description: "Quickstart with the API"
---

This backend API uses Express and MySQL under the hood. To start with developing and testing, you can clone the repository first using `git clone`:

```bash
git clone https://github.com/vickysalim/BangkitCapstone-CS23-PS315 capstone-cs23-ps315
```

The folder for CC team and the backend itself is in the `cloud-computing` folder. Make sure to enter those folder(s) first:

```bash
cd ./capstone-cs23-ps315/cloud-computing

# to the backend folder
cd ./backend

# to the docs folder
cd ./docs
```

## Prerequisites

You will need:

- A working install of MariaDB (MySQL) running on the default port with the database name of your choice (make sure you create the database first and put it in the `.env` file).
- Node.js with the minimum working version of 16. More recent versions are recommended.
- GCP credential and bucket name (set it in the `.env` file also).

## Installing Dependencies

Before you run the project, make sure to install the dependencies first. Here, we encourage the usage of `pnpm` as the package manager for the `npm` packages. [Here's a bit of reasoning on why we use `pnpm` for our projects](https://blog.bitsrc.io/pnpm-javascript-package-manager-4b5abd59dc9). But you can also use another package manager like `npm` or `yarn`. The choice is yours, really.

```bash
pnpm install
```

## Starting in Development Mode

To start the project in the development mode, run this command:

```bash
pnpm run dev

# or

pnpm dev
```

If you make any changes, the server will automatically watch and restart to accomodate those changes.

## Building

You can build (we use Typescript, needs to be transpiled into JavaScript first before we can start serving for obvious reasons) and serve the production output using this command:

```bash
pnpm run build

# or

pnpm build
```

## Serving the Transpiled Build

You can serve the transpiled build (the output path is in `./dist`) using regular `node` command:

```bash
node ./dist/index.js
```
