

// fit the two pyramids together 
// back to back with the peak 
// in the z coord sticking out.
// The green object is the ground,
// the red objec is the brick.
// Then stack the blue object onto the red.

color( "LimeGreen", a=0.5 )
polyhedron(
points=[[2,0,0],[5,0,0],[2,4,0],[2,0,-1]], 
faces=[[0,1,2],[0,3,1],[0,2,3],[1,3,2]]);


translate(v=[2,0,0])
rotate( a=120, v=[-1, -1, -1])
color( "Red", a=0.5 ) 
polyhedron(
points=[[0,0,0],[0,3,0],[0,0,4],[1,0,0]], 
faces=[[0,1,2],[0,3,1],[0,2,3],[1,3,2]]);


//translate(v=[2.57,0.4,1.71])
// rotate( a=45.3, v=[-3, 4, 0] )
// rotate( a=-120, v=[1, 1, 1])
//rotate( a=-120, v=[7,4,2] )
//rotate( a= -120, v=[0.7298,0.417028,0.208515] )

//translate(v=[2.5,0,1.227])
translate(v=[1.56, 0.34, 1.38])
rotate( a=120, v=[-0.73,-0.42, -0.19] )
color( "Blue", a=0.5 ) 
polyhedron(
points=[[0,0,0],[0,3,0],[0,0,4],[-1,0,0]], 
faces=[[0,1,2],[0,3,1],[0,2,3],[1,3,2]]);