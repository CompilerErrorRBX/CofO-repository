local Part = Instance.new("BasePart")
Part:setParent(game.Workspace)
Part.color = Color3.new(255, 255, 0)
Part.transparency = math.random(0.0, 100)/100

local Mesh = Instance.new("Mesh")
Mesh:setMeshId("3DModels/MonkeyHead.obj")
Mesh:setParent(Part)