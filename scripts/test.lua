local Part = Instance.new("BasePart")
Part.color = Color3.new(255, 255, 0)
Part.transparency = math.random(0, 100)/100

local Mesh = Instance.new("Mesh")
Mesh:setMeshId("3DModels/MonkeyHead.obj")

Part:setParent(game.Workspace)
Mesh:setParent(Part)

local MeshIds = {
	"3DModels/MonkeyHead.obj",
	"3DModels/Sphere.obj",
	"3DModels/Cube.obj",
}

while true do
	wait(1);
	Mesh:setMeshId(MeshIds[math.random(1, #MeshIds)])
end
