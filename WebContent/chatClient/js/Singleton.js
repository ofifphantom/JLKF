var Singleton = (function() {
	function SingletonConstrucotr() {
		var div = document.createElement("div");
		div.className = "mask";
		div.style.width = "100%";
		div.style.height = "100%";
		div.style.background = "rgba(50,50,50,0.5)";
		div.style.position = "fixed";
		div.style.top = "0";
		div.style.left = "0";
		div.style.display = "flex";
		div.style.justifyContent = "center";
		div.style.alignItems = "center";
		var img = document.createElement("img");
		img.style.maxWidth="100%";
		img.style.maxHeight="100%";
		div.appendChild(img);
		document.body.appendChild(div);

		return div;
	}
	var instance = null;
	return {
		getInstance: function(mysrc) {
			!instance && (instance = new SingletonConstrucotr());
			instance.children[0].src = mysrc;
			instance.style.display = "flex";
			instance.addEventListener('touchend',function () {
			        instance.style.display = "none";
			})
			return instance;
		}
	}
})();