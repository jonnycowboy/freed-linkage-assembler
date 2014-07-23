

multmatrix(m = [
[1.0000000000, 0.0000000000, 0.0000000000, 0.0000000000], 
[0.0000000000, 1.0000000000, 0.0000000000, 0.0000000000], 
[0.0000000000, 0.0000000000, 1.0000000000, 0.0000000000],
[0, 0, 0, 1]]) {
color( "Red", a=0.5 ) 
polyhedron(
points=[
[3467.85, 43.0687, 302.5],
[3455.57, 5.0, 332.5],
[3455.57, 5.0, 302.5],
[3455.57, 5.0, 300.0]],
faces=[[0,1,2],[0,3,1],[0,2,3],[1,3,2]]);

//	import_stl(filename = "STL\\BOOM_EX_375.stl");
}

//multmatrix(m = [
// [1.0000000000, 0.0000000000, 0.0000000000, 12117.36], 
// [0.0000000000, 1.0000000000, 0.0000000000, -4645.4413],
// [0.0000000000, 0.0000000000, 1.0000000000, -297.5],
// [0.0000000000, 0.0000000000, 0.0000000000, 1.0000000000] ]) 
translate(v=[
-6096.966798571706,
 2121.699201037429,
 1464.2762736936115])
rotate(a=165.11, v=[-0.27457, -0.5335, -0.78941])
{ 
color( "Blue", a=0.5 ) 
polyhedron(
points=[
[-8649.51, 4688.51, 600.0],
[-8625.71, 4720.65, 570.0],
[-8625.71, 4720.65, 600.0],
[-8625.71, 4720.65, 601.0]],
faces=[[0,1,2],[0,3,1],[0,2,3],[1,3,2]]);
//	import_stl(filename = "STL\\BOOM.stl");

}