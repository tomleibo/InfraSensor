jsPlumb.ready(function() {
	var connectorPaintStyle = {
		lineWidth:4,
		strokeStyle:"#61B7CF",
		joinstyle:"round",
		outlineColor:"white",
		outlineWidth:2
	},
	// .. and this is the hover style. 
	connectorHoverStyle = {
		lineWidth:4,
		strokeStyle:"#216477",
		outlineWidth:2,
		outlineColor:"white"
	},
	endpointHoverStyle = {
		fillStyle:"#216477",
		strokeStyle:"#212477"
	},
	exampleColor = '#7AB02C',
		exampleDropOptions = {
				tolerance:'touch',
				hoverClass:'dropHover',
				activeClass:'dragActive'
		}, 
		connector = [ "Bezier", { cssClass:"connectorClass", hoverClass:"connectorHoverClass" } ],
		connectorStyle = {
			gradient:{stops:[[0, exampleColor], [0.5, '#11B11C'], [1, "#00f"]]},
			lineWidth:5,
			strokeStyle:exampleColor
		},
		overlays = [ ["Diamond", { fillStyle:"#09098e", width:15, length:15 } ] ],
		endpoint = ["Dot", { cssClass:"endpointClass", radius:10, hoverClass:"endpointHoverClass" } ],
		endpointStyle = { fillStyle:exampleColor },
sourceEndpoint = {
		
		endpoint:["Dot", {radius:1}],
		paintStyle:{ 
			strokeStyle:"#7AB02C",
			fillStyle:"transparent",
			radius:7,
			maxConnections:-1, 
			lineWidth:3 
		},				
		isSource:true,
		connector:[ "Bezier", { cssClass:"connectorClass", hoverClass:"connectorHoverClass" } ],								                
		connectorStyle:connectorStyle,
		hoverPaintStyle:endpointHoverStyle,
		maxConnections:-1,
		connectorOverlays:[[ "Arrow", { location:0.7 }, arrowCommon ]],
		connectorHoverStyle:connectorHoverStyle,


	},		
	
	// the definition of target endpoints (will appear when the user drags a connection) 
	targetEndpoint = {
		endpoint:"Dot",		
		maxConnections:-1, 		
		paintStyle:{ fillStyle:"#7AB02C",radius:11 },
		hoverPaintStyle:endpointHoverStyle,
		maxConnections:-1,
		dropOptions:{ hoverClass:"hover", activeClass:"active" },
		connectorOverlays:[[ "Arrow", { location:0.7 }, arrowCommon ]],
		isTarget:true,			
        overlays:[
        	[ "Label", { location:[0.5, -0.5], label:"Drop", cssClass:"endpointTargetLabel" } ]
        ]
	};
	var arrowCommon = { foldback:0.7,  width:30 };
	var sourceAnchors = [[0.2, 0, 0, -1, 0, 0, "foo"], [1, 0.2, 1, 0, 0, 0, "bar"], [0.8, 1, 0, 1, 0, 0, "baz"], [0, 0.8, -1, 0, 0, 0, "qux"] ],
		targetAnchors = [[0.6, 0, 0, -1], [1, 0.6, 1, 0], [0.4, 1, 0, 1], [0, 0.4, -1, 0] ],

		exampleColor = '#00f',
		exampleDropOptions = {
				tolerance:'touch',
				hoverClass:'dropHover',
				activeClass:'dragActive'
		}, 
		connector = [ "Bezier", { cssClass:"connectorClass", hoverClass:"connectorHoverClass" } ],
		connectorStyle = {
			gradient:{stops:[[0, exampleColor], [0.5, '#09098e'], [1, exampleColor]]},
			lineWidth:5,
			strokeStyle:exampleColor
		},
		hoverStyle = {
			strokeStyle:"#449999"
		},
		overlays = [ ["Diamond", { fillStyle:"#09098e", width:15, length:15 } ] ],
		endpoint = ["Dot", { cssClass:"endpointClass", radius:10, hoverClass:"endpointHoverClass" } ],
		endpointStyle = { fillStyle:exampleColor },
		anEndpoint = {
			endpoint:endpoint,
			connectorOverlays:[[ "Arrow", { location:0.7 }, arrowCommon ]],
			paintStyle:endpointStyle,
			hoverPaintStyle:{ fillStyle:"#449999" },
			isSource:true, 
			isTarget:true, 
			maxConnections:-1, 
			connector:connector,
			connectorStyle:connectorStyle,
			connectorHoverStyle:hoverStyle,
		};
	
	var instance = jsPlumb.getInstance({
		DragOptions : { cursor: 'pointer', zIndex:2000 },
		Container:"dynamic-demo"
	});

	// suspend drawing and initialise.
	instance.doWhileSuspended(function() {

		var connections = {
			"dynamicWindow1":["dynamicWindow4"],
			"dynamicWindow3":["dynamicWindow1"],
			"dynamicWindow5":["dynamicWindow3"],
			"dynamicWindow6":["dynamicWindow5"],
			"dynamicWindow2":["dynamicWindow6"],
			"dynamicWindow4":["dynamicWindow2"]
			
		},
		endpoints = {},			
		// ask jsPlumb for a selector for the window class
		divsWithWindowClass = jsPlumb.getSelector(".dynamic-demo .window");
		
		// add endpoints to all of these - one for source, and one for target, configured so they don't sit
		// on top of each other.
		for (var i = 0 ; i < divsWithWindowClass.length; i++) {
			var id = instance.getId(divsWithWindowClass[i]);
			endpoints[id] = [
				// note the three-arg version of addEndpoint; lets you re-use some common settings easily.
				instance.addEndpoint(id, sourceEndpoint, {anchor:sourceAnchors}),
				instance.addEndpoint(id, targetEndpoint, {anchor:targetAnchors})
			];
		}
		// then connect everything using the connections map declared above.
		for (var e in endpoints) {
			if (connections[e]) {
				for (var j = 0; j < connections[e].length; j++) {					
					instance.connect({
						source:endpoints[e][0],
						target:endpoints[connections[e][j]][1]
					});						
				}
			}	
		}
		
		// bind click listener; delete connections on click			
		instance.bind("click", function(conn) {
			instance.detach(conn);
		});
		
		// bind beforeDetach interceptor: will be fired when the click handler above calls detach, and the user
		// will be prompted to confirm deletion.
		instance.bind("beforeDetach", function(conn) {
			return confirm("Delete connection?");
		});
		
		//
		// configure ".window" to be draggable. 'getSelector' is a jsPlumb convenience method that allows you to
		// write library-agnostic selectors; you could use your library's selector instead, eg.
		//
		// $(".window")  		jquery
		// $$(".window") 		mootools
		// Y.all(".window")		yui3
		//
		instance.draggable(divsWithWindowClass);	

		jsPlumb.fire("jsPlumbDemoLoaded", instance);
	});
});
