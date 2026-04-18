using appVelascoApi.accessData.context;
using appVelascoEntitys.Models;
using Microsoft.EntityFrameworkCore;


namespace appVelascoServices
{
    public class CrudGenericServices<TEntityBase> where TEntityBase : EntityBase
    {
        private readonly DataBaseVelascoContext _context;

        public CrudGenericServices(DataBaseVelascoContext context)
        {
            _context = context;
        } 

        public async Task<TEntityBase> InsertEntity(TEntityBase entity)
        {
             await _context.Set<TEntityBase>().AddAsync(entity);
             _context.Entry(entity).State = EntityState.Added;
            await _context.SaveChangesAsync();
             return entity;
            
        }

        public async Task<TEntityBase> SelectEntity(int id) 
        {
            var entity = await _context.Set<TEntityBase>().SingleOrDefaultAsync(p=> p.Id == id && p!.Estado);
            if (entity == null) return null;
            return entity;

        }   
        
        public async Task<List<TEntityBase>> SelectEntityAll()
        {
            var entities = await _context.Set<TEntityBase>().ToListAsync();
            if(entities == null) return null;
            return entities;
        }

        public async Task<TEntityBase> UpdateEntity(int id, TEntityBase entity)
        {
            var existingEntity = await _context.Set<TEntityBase>().FindAsync(id);
            if (existingEntity != null)
            {
                _context.Entry(existingEntity).CurrentValues.SetValues(entity);
                await _context.SaveChangesAsync();
                return existingEntity;
            }
            return null;
        }

        public async Task<bool> DeleteEntity(int id)
        {
            // Buscamos la entidad por ID
            var entity = await _context.Set<TEntityBase>().SingleOrDefaultAsync(p => p.Id == id);

            if (entity == null) return false;

            // --- CAMBIO PARA BORRADO LÓGICO ---
            // En lugar de EntityState.Deleted, cambiamos el flag de Estado
            entity.Estado = false;

            // Opcional: Si quieres marcarla como modificada explícitamente
            _context.Entry(entity).State = EntityState.Modified;

            await _context.SaveChangesAsync();
            return true;
        }
    }
}
