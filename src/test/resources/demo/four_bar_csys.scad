

translate(v = [0.0000000000, 0.0000000000, 0.0000000000]) {
	rotate(a = 0.0000000000, v = [0.0000000000, 0.0000000000, 0.0000000000]) {
		import_stl(filename = "STL\\BAR_1.stl");
	}
}

translate(v = [-50.0000000000, -50.0000000000, -12.80000000000]) {
	rotate(a = 90.0000000000, v = [0.0000000000, -0.0, 1.0000000000]) {
		import_stl(filename = "STL\\BAR_1_Z1.stl");
	}
}

translate(v = [-100.0000000000, -100.0000000000, -0.0000000000]) {
	rotate(a = 0.0000000000, v = [0.0000000000, 0.0000000000, 0.0000000000]) {
		import_stl(filename = "STL\\BAR_1_Z2.stl");
	}
}

translate(v = [-150.0000000000, -150.0000000000, -12.80000000000]) {
	rotate(a = 90.0000000000, v = [0.0000000000, -0.0, 1.0000000000]) {
		import_stl(filename = "STL\\BAR_1_Z3.stl");
	}
}