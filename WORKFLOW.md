# 🚀 Development Workflow

This project follows a **feature branch + main branch** workflow for clean, maintainable development.

## 📋 Workflow Overview

```
main branch (production-ready)
    ↑
Pull Request (code review)
    ↑
feature branch (development)
    ↑
local development
```

## 🔄 Workflow Steps

### 1. Starting Development

```bash
# Ensure you're on main and up to date
git checkout main
git pull origin main

# Create a new feature branch
git checkout -b feature/your-feature-name

# Start development
# ... make your changes ...
```

### 2. Development Process

```bash
# Make your changes
# Run tests locally
mvn clean test

# Commit your changes with conventional commits
git add .
git commit -m "feat: add new cyclic dependency detection"

# Push your feature branch
git push origin feature/your-feature-name
```

### 3. Pull Request Process

1. **Create Pull Request**
   - Go to GitHub repository
   - Click "New Pull Request"
   - Select `feature/your-feature-name` → `main`
   - Fill in PR description

2. **Automated CI**
   - GitHub Actions automatically runs:
     - Build and test
     - Quality checks (PMD, SpotBugs, Checkstyle)
     - Security scanning
     - Code coverage

3. **Code Review**
   - Maintainers review your code
   - Address any feedback
   - Update PR as needed

4. **Merge to Main**
   - Once approved, merge to main
   - Feature branch can be deleted

### 4. Release Process

```bash
# Ensure main is ready for release
git checkout main
git pull origin main

# Create release via GitHub Actions
# 1. Go to Actions tab
# 2. Run "Release" workflow
# 3. Choose version and release type
# 4. Review and approve
```

## 📝 Commit Convention

Use [Conventional Commits](https://www.conventionalcommits.org/) format:

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### Types
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `perf`: Performance improvements
- `test`: Adding or updating tests
- `build`: Build system changes
- `ci`: CI/CD changes
- `chore`: Maintenance tasks

### Examples
```bash
git commit -m "feat: add configurable exclusion patterns"
git commit -m "fix: resolve compilation error in enforcer rule"
git commit -m "docs: update README with usage examples"
git commit -m "test: add integration tests for cyclic detection"
```

## 🔧 Branch Naming

Use descriptive branch names:

```
feature/add-exclusion-patterns
feature/performance-optimization
fix/compilation-error
docs/update-readme
test/add-integration-tests
```

## 🚫 What Not to Do

- ❌ Don't commit directly to main
- ❌ Don't merge without code review
- ❌ Don't skip tests
- ❌ Don't use vague commit messages
- ❌ Don't leave feature branches open indefinitely

## ✅ Best Practices

- ✅ Always create feature branches for changes
- ✅ Write clear commit messages
- ✅ Run tests before pushing
- ✅ Keep PRs small and focused
- ✅ Respond to review feedback promptly
- ✅ Delete feature branches after merge
- ✅ Update documentation when needed

## 🎯 Workflow Benefits

1. **Code Quality**: All changes reviewed before merge
2. **Collaboration**: Clear process for team contributions
3. **Stability**: Main branch always contains working code
4. **Traceability**: Clear history of changes and releases
5. **Automation**: CI/CD handles testing and quality checks
6. **Flexibility**: Easy to work on multiple features simultaneously 