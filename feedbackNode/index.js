"use static";

const bodyparser = require('body-parser');
const path = require('path');
const express = require('express');
const app = express();

const port = 8090;
const prefix = '/feedbackMessage/';
const dataDir = path.join(__dirname, '/feedbackMessages/');

const fs = require('fs');

app.use(bodyparser.json());

app.post(prefix + ':deviceId', (req, res) => {
	var deviceId = req.params.deviceId;

	var sanitizedDeviceId = deviceId.replace(/\W/g, '');
	var dataFile = path.join(dataDir, sanitizedDeviceId + '.txt');
	var message = new Date() + ": " + JSON.stringify(req.body) + '\n';
	fs.appendFile(dataFile, message, (err) => {
		if (err) {
			console.error("failed to write to file " + dataFile, err);
			return res.status(500).end();
		}
		console.log(new Date(), "new action from device " + deviceId, req.body);
		res.status(204).end();
	});
});

app.listen(port, (e) => {
	if (e) {
		console.error(e);
	}
	console.log("started server on port %d", port);
})
