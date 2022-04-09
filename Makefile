build:
	docker-compose build
local:
	docker-compose -f docker-compose.yaml -f docker-compose.local.yaml up -d
dev:
	docker-compose -f docker-compose.yaml -f docker-compose.dev.yaml up -d
prod:
	docker-compose -f docker-compose.yaml -f docker-compose.prod.yaml up -d
down:
	docker-compose down
db:
	docker-compose up -d db
